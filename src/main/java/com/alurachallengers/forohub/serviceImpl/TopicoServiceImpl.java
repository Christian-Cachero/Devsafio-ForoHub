package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.exceptions.TopicoDuplicadoException;
import com.alurachallengers.forohub.exceptions.EntityNoExistsException;
import com.alurachallengers.forohub.model.Respuesta;
import com.alurachallengers.forohub.model.Topico;
import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import com.alurachallengers.forohub.model.mappers.TopicoMapper;
import com.alurachallengers.forohub.repository.RespuestaRepository;
import com.alurachallengers.forohub.repository.TopicoRepository;
import com.alurachallengers.forohub.repository.UsuarioRepository;
import com.alurachallengers.forohub.service.TopicoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
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
    public List<TopicoDTO> getAllTopicosDTO() {
        return topicoMapper.toTopicoDTOs(topicoRepository.findAll());
    }

    @Override
    public Page<TopicoDTO> getAllTopicosDTO(Pageable pageable) {
        return topicoMapper.toTopicoDTOs(topicoRepository.findAll(pageable));
    }

    @Override
    public Optional<TopicoDTO> getTopicoById(Long id) {

        Optional<TopicoDTO> topicoDTO = topicoRepository.findById(id).map(topicoMapper::toTopicoDTO);

        if (topicoDTO.isPresent()){
            return topicoDTO;
        }
        else {
            throw new EntityNoExistsException("No existe tal tópico.");
        }
    }

    @Override
    public TopicoDTO createTopico(Long usuarioId, TopicoDTO topicoDTO) {
        if (topicoRepository.findByTituloAndMensaje(topicoDTO.titulo(), topicoDTO.mensaje()).isPresent()) {
            throw new TopicoDuplicadoException("El tópico con este título y contenido ya existe.");
        }

        Usuario autor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear y guardar el nuevo tópico
        Topico topico = topicoMapper.toTopico(topicoDTO);
        topico.setAutor(autor);
        Topico nuevoTopico = topicoRepository.save(topico);


        return topicoMapper.toTopicoDTO(nuevoTopico);
    }


    @Override
    @SneakyThrows
    public Optional<TopicoDTO> updateTopico(Long id, TopicoDTO topicoDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long autorId = ((Usuario) authentication.getPrincipal()).getId();

        if (topicoRepository.existsById(id)){
            Topico topicoAModificar = topicoRepository.findById(id)
                    .orElseThrow(() -> new EntityNoExistsException("Tópico no existe"));

            if (!topicoAModificar.getAutor().getId().equals(autorId)) {
                throw new AccessDeniedException("No eres el autor de este tópico");
            }

            if (topicoDTO.titulo() != null){
                topicoAModificar.setTitulo(topicoDTO.titulo());
            }

            if (topicoDTO.mensaje() != null){
                topicoAModificar.setMensaje(topicoDTO.mensaje());
                // Actualiza vistaPrevia en las respuestas:
                Optional.ofNullable(topicoAModificar.getRespuestas())
                        .ifPresent(respuestas -> respuestas
                                .forEach(respuesta -> respuesta.setVistaPreviaMensaje(topicoDTO.mensaje())));
            }

            if (topicoDTO.estado() != null){
                topicoAModificar.setEstado(topicoDTO.estado());
            }

            if (topicoDTO.curso() != null){
                topicoAModificar.setCurso(topicoDTO.curso());
            }

            Topico topicoModificado = topicoRepository.save(topicoAModificar);
            return Optional.of(topicoMapper.toTopicoDTO(topicoModificado));
        }

        throw new EntityNoExistsException("El Tópico seleccionado no existe");
    }

    @Override
    public void deleteTopico(Long id) {
        if(topicoRepository.existsById(id)){
            topicoRepository.deleteById(id);
        }
        else {
            throw new EntityNoExistsException("El Tópico seleccionado no existe");
        }
    }
}
