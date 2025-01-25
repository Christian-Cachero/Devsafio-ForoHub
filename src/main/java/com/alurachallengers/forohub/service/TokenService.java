package com.alurachallengers.forohub.service;


import com.alurachallengers.forohub.model.Usuario;
import com.auth0.jwt.interfaces.DecodedJWT;

public interface TokenService {

    String generarToken(Usuario usuario);

    DecodedJWT validateToken(String token);

    String extractUsername(DecodedJWT decodedJWT);
}
