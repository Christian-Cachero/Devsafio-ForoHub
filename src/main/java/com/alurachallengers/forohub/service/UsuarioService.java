package com.alurachallengers.forohub.service;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.AuthLoginRequest;
import com.alurachallengers.forohub.model.dtos.DatosJWTtoken;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    List<UsuarioDTO> getAllUsuariosDTO();
    Usuario createUsuario(Usuario usuario);

    DatosJWTtoken login(AuthLoginRequest authLoginRequest);
}
