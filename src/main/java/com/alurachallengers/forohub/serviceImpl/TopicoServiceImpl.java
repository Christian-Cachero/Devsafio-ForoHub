package com.alurachallengers.forohub.serviceImpl;

import com.alurachallengers.forohub.exceptions.TopicoDuplicadoException;
import com.alurachallengers.forohub.exceptions.TopicoNoExistsException;
import com.alurachallengers.forohub.model.Topico;
import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import com.alurachallengers.forohub.model.mappers.TopicoMapper;
import com.alurachallengers.forohub.repository.TopicoRepository;
import com.alurachallengers.forohub.repository.UsuarioRepository;
import com.alurachallengers.forohub.service.TopicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            throw new TopicoNoExistsException("No existe tal tópico.");
        }
    }

    @Override
    public TopicoDTO createTopico(long usuarioId, TopicoDTO topicoDTO) {
        // Verificar si el tópico ya existe
        if (topicoRepository.findByTituloAndMensaje(topicoDTO.titulo(), topicoDTO.mensaje()).isPresent()) {
            throw new TopicoDuplicadoException("El tópico con este título y contenido ya existe.");
        }

        // Obtener el autor
        Usuario autor = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear y guardar el nuevo tópico
        Topico topico = topicoMapper.toTopico(topicoDTO);
        topico.setAutor(autor);
        Topico nuevoTopico = topicoRepository.save(topico);

        // Retornar el DTO del nuevo tópico
        return topicoMapper.toTopicoDTO(nuevoTopico);
    }

    @Override
    public Optional<TopicoDTO> updateTopico(Long id, TopicoDTO topicoDTO) {
        if (topicoRepository.existsById(id)){
            Topico topicoAModificar = topicoRepository.findById(id)
                    .orElseThrow(() -> new TopicoNoExistsException("Tópico no existe"));

            if (topicoDTO.titulo() != null){
                topicoAModificar.setTitulo(topicoDTO.titulo());
            }

            if (topicoDTO.mensaje() != null){
                topicoAModificar.setMensaje(topicoDTO.mensaje());
            }

            if (topicoDTO.estado() != null){
                topicoAModificar.setEstado(topicoDTO.estado());
            }

            Topico topicoModificado = topicoRepository.save(topicoAModificar);
            return Optional.of(topicoMapper.toTopicoDTO(topicoModificado));
        }

        throw new TopicoNoExistsException("El Tópico seleccionado no existe");
    }

    @Override
    public void deleteTopico(Long id) {
        if(topicoRepository.existsById(id)){
            topicoRepository.deleteById(id);
        }
        else {
            throw new TopicoNoExistsException("El Tópico seleccionado no existe");
        }
    }
}
