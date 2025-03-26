package com.alurachallengers.forohub.model.dtos;

import java.time.LocalDate;

public record RespuestaDTO(
        Long id,
        LocalDate fechaCreacion,
        String respondeA,
        String nombreAutor,
        String contenidoRespuesta) {
}
