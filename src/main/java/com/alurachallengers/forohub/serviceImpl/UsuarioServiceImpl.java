package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;
import com.alurachallengers.forohub.model.mappers.UsuarioAuthMapper;
import com.alurachallengers.forohub.model.mappers.UsuarioMapper;
import com.alurachallengers.forohub.repository.UsuarioRepository;
import com.alurachallengers.forohub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Usuario validarUsuarioPorEmail(Usuario usuario){
        return usuarioRepository.findByEmail(usuario.getEmail());
    }
}
