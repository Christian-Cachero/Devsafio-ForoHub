package com.alurachallengers.forohub.model;

import com.alurachallengers.forohub.model.enums.EstadoTopico;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Este campo es obligatorio.")
    private String titulo;

    @NotNull(message = "Este campo es obligatorio.")
    private String mensaje;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaCreacion;

    //@JsonIgnore
    @Enumerated(EnumType.STRING)
    private EstadoTopico estado;

    @ManyToOne
    @NotNull(message = "Este campo es obligatorio.")
    //@JsonBackReference
    private Usuario autor;

    @PrePersist
    protected void onCreate(){
        fechaCreacion = LocalDate.now();
    }
}
