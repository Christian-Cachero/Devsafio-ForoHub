package com.alurachallengers.forohub.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

    /*TODO: hacer un DTO para que no me de un overflow y
     * desactivar las anotaciones que deje de momento para esto*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Este campo es obligatorio.")
    private String nombre;

    @NotNull(message = "Este campo es obligatorio.")
    private String email;

    @NotNull(message = "Este campo es obligatorio.")
    private Integer contrasena;

    @OneToMany(mappedBy = "autor")
    @JsonManagedReference
    private List<Topico> topicos = new ArrayList<>();
}
