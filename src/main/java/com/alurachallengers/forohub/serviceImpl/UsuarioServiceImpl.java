package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.AuthLoginRequest;
import com.alurachallengers.forohub.model.dtos.DatosJWTtoken;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;
import com.alurachallengers.forohub.model.mappers.UsuarioAuthMapper;
import com.alurachallengers.forohub.model.mappers.UsuarioMapper;
import com.alurachallengers.forohub.repository.UsuarioRepository;
import com.alurachallengers.forohub.service.TokenService;
import com.alurachallengers.forohub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private UsuarioAuthMapper usuarioAuthMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public List<UsuarioDTO> getAllUsuariosDTO() {
        return usuarioMapper.toUsuariosDTOs(usuarioRepository.findAll());
    }

    @Override
    public Usuario createUsuario(Usuario usuario) {
        if (validarUsuarioPorEmail(usuario) != null) {
            throw new RuntimeException("El usuario con este mail ya existe");
        }

        usuario.setClave(passwordEncoder.encode(usuario.getClave()));

        return usuarioRepository.save(usuario);
    }

    @Override
    public DatosJWTtoken login(AuthLoginRequest authLoginRequest) {
        if (authLoginRequest.email() == null || authLoginRequest.clave() == null) {
            throw new IllegalArgumentException("Email y clave no pueden ser nulos");
        }

        try {
            // Crear el token de autenticación
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    authLoginRequest.email(),
                    authLoginRequest.clave()
            );
            // Autenticar al usuario
            var usuarioAutenticado = authenticationManager.authenticate(authenticationToken);
            var token = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());

            // Generar el token JWT
            return new DatosJWTtoken(token);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Error de autenticación: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado durante el proceso de login: " + e.getMessage(), e);
        }
    }

    public Usuario validarUsuarioPorEmail(Usuario usuario){
        return usuarioRepository.findByEmail(usuario.getEmail());
    }
}
