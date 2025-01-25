package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.service.TokenService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    @Value("&{security.jwt.user.generator}")
    private String userGenerator;

    @Override
    public String generarToken(Usuario usuario) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            return JWT.create()
                    .withIssuer(userGenerator)
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withIssuedAt(new Date())
                    .withExpiresAt(generarFechaExpiracion())
                    .withJWTId(UUID.randomUUID().toString())
                    .withNotBefore(new Date(System.currentTimeMillis()))
                    .sign(algorithm);
        }catch (JWTCreationException e){
            throw new JWTCreationException("No se pudo crear el token", e);
        }
    }

    @Override
    public DecodedJWT validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(userGenerator)
                    .build();

            return verifier.verify(token);
        }catch(JWTVerificationException e){
            throw new JWTVerificationException("Verificación fallida,Token invalido");
        }
    }

    public String extractUsername(DecodedJWT decodedJWT){
        return decodedJWT.getSubject();
    }

    private Instant generarFechaExpiracion(){
        return LocalDateTime.now().plusHours(6).toInstant(ZoneOffset.of("-03:00"));
    }
}
