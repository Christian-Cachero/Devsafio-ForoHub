package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioAuthDTO;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;
import com.alurachallengers.forohub.model.mappers.UsuarioAuthMapper;
import com.alurachallengers.forohub.model.mappers.UsuarioMapper;
import com.alurachallengers.forohub.repository.UsuarioRepository;
import com.alurachallengers.forohub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> getAllUsuariosDTO() {
        return usuarioMapper.toUsuariosDTOs(usuarioRepository.findAll());
    }

    @Override
    public Usuario createUsuario(UsuarioAuthDTO usuarioAuthDTO) {
        if (validarUsuarioPorEmail(usuarioAuthMapper.toUsuario(usuarioAuthDTO)) != null){
            throw new RuntimeException("El usuario con este mail ya existe");
        }

        Usuario nuevoUsuario = usuarioAuthMapper.toUsuario(usuarioAuthDTO);

        nuevoUsuario.setClave(passwordEncoder.encode(usuarioAuthDTO.clave()));

        return usuarioRepository.save(nuevoUsuario);
    }

    public Usuario validarUsuarioPorEmail(Usuario usuario){
        return usuarioRepository.findByEmail(usuario.getEmail());
    }
}
