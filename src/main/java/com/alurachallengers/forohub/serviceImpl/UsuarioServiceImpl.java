package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;
import com.alurachallengers.forohub.model.mappers.UsuarioMapper;
import com.alurachallengers.forohub.repository.UsuarioRepository;
import com.alurachallengers.forohub.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public List<UsuarioDTO> getAllUsuariosDTO() {
        return usuarioMapper.toUsuariosDTOs(usuarioRepository.findAll());
    }

    @Override
    public Optional<Usuario> createUsuario(Usuario usuario) {

        if (validadUsuarioPorEmail(usuario) == null) {
            return Optional.of(usuarioRepository.save(usuario));
        }
        else {
            return Optional.empty();
        }
    }

    public Usuario validadUsuarioPorEmail(Usuario usuario){
        return usuarioRepository.findByEmail(usuario.getEmail());
    }
}
