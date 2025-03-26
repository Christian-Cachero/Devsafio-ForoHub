package com.alurachallengers.forohub.service;

import com.alurachallengers.forohub.model.dtos.RespuestaDTO;

import java.util.List;
import java.util.Optional;

public interface RespuestaService {

    List<RespuestaDTO> getAllRespuestasDTO();
    Optional<RespuestaDTO> getRespuestaById(Long id);
    RespuestaDTO createRespuesta(Long topicoId, RespuestaDTO respuestaDTO);
    Optional<RespuestaDTO> updateRespuesta(Long id, RespuestaDTO respuestaDTO);
    void deleteRespuesta(Long id);
}
