package com.alurachallengers.forohub.model.dtos;

import com.alurachallengers.forohub.model.Usuario;



public record TopicoDTO(String titulo, String mensaje, Usuario autor) {


    public String getTitulo() {
        return titulo;
    }


    public String getMensaje() {
        return mensaje;
    }

    public Usuario getAutor() {
        return autor;
    }
}
