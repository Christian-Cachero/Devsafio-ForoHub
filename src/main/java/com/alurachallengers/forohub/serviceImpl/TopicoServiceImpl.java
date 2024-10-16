package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.exceptions.TopicoDuplicadoException;
import com.alurachallengers.forohub.model.Topico;
import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import com.alurachallengers.forohub.model.mappers.TopicoMapper;
import com.alurachallengers.forohub.repository.TopicoRepository;
import com.alurachallengers.forohub.repository.UsuarioRepository;
import com.alurachallengers.forohub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicoServiceImpl implements TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TopicoMapper topicoMapper;

    @Override
    public List<Topico> getAllTopicos() {
        return topicoRepository.findAll();
    }

    @Override
    public TopicoDTO createTopico(long usuarioId, TopicoDTO topicoDTO) {
        //v1:
        Optional<Topico> topicoRepetido = topicoRepository.
                findByTituloAndMensaje(topicoDTO.getTitulo(), topicoDTO.getMensaje());

        if (topicoRepetido.isPresent()){
            throw new TopicoDuplicadoException("El tópico con este título y contenido ya existe.");
        }
        else {
            Usuario autor = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            Topico topico = topicoMapper.toTopico(topicoDTO);
            topico.setAutor(autor);

            Topico nuevoTopico = topicoRepository.save(topico);
            return topicoMapper.toTopicoDTO(nuevoTopico);
        }
    }
}
