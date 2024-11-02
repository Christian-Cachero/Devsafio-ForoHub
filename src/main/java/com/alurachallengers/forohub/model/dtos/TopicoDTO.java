package com.alurachallengers.forohub.model.dtos;

import com.alurachallengers.forohub.model.enums.EstadoTopico;

import java.time.LocalDate;


public record TopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDate fechaCreacion,
        EstadoTopico estado,
        UsuarioDTO autor) {
}
