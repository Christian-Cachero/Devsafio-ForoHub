package com.alurachallengers.forohub.service;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {


    List<UsuarioDTO> getAllUsuariosDTO();
    Usuario createUsuario(UsuarioDTO usuario);
}
