package com.alurachallengers.forohub.service;

import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface TopicoService {

    List<TopicoDTO> getAllTopicosDTO();

    Page<TopicoDTO> getAllTopicosDTO(Pageable pageable);

    TopicoDTO createTopico(Long usuarioId, TopicoDTO topicoDTO);

    Optional<TopicoDTO> getTopicoById(Long id);

    Optional<TopicoDTO> updateTopico(Long id, TopicoDTO topicoDTO);
    void deleteTopico(Long id);

}
