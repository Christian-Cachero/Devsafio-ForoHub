package com.alurachallengers.forohub.model.dtos;

import com.alurachallengers.forohub.model.enums.Curso;
import com.alurachallengers.forohub.model.enums.EstadoTopico;

import java.time.LocalDate;


public record TopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        Curso curso,
        EstadoTopico estado,
        LocalDate fechaCreacion,
        UsuarioDTO autor) {
}
