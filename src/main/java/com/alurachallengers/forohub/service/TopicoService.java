package com.alurachallengers.forohub.service;

import com.alurachallengers.forohub.model.Topico;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;

import java.util.List;
import java.util.Optional;

public interface TopicoService {

    List<Topico> getAllTopicos();

    TopicoDTO createTopico(long usuarioId, TopicoDTO topicoDTO);


/*    Optional<Topico> getTopicoById(Long id);

    Optional<Topico> updateTopico(Long id, Topico topico);

    void deleteTopico(Long id);*/

}
