package com.alurachallengers.forohub.service;

import com.alurachallengers.forohub.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {


    List<Usuario> getAllUsuarios();
    Optional<Usuario> createUsuario(Usuario usuario);
}
