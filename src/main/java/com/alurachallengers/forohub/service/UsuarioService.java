package com.alurachallengers.forohub.service;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;
import com.alurachallengers.forohub.model.dtos.UsuarioAuthDTO;

import java.util.List;

public interface UsuarioService {


    List<UsuarioDTO> getAllUsuariosDTO();
    Usuario createUsuario(UsuarioAuthDTO usuario);
}
