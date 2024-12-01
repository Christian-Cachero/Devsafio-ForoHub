package com.alurachallengers.forohub.model;

import com.alurachallengers.forohub.model.enums.EstadoTopico;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Este campo es obligatorio.")
    private String titulo;

    @NotNull(message = "Este campo es obligatorio.")
    private String mensaje;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @CreationTimestamp
    private LocalDate fechaCreacion;

    //@JsonIgnore
    @Enumerated(EnumType.STRING)
    private EstadoTopico estado;

    @ManyToOne
    @NotNull(message = "Este campo es obligatorio.")
    //@JsonBackReference
    private Usuario autor;

/*    @PrePersist
    protected void onCreate(){
        fechaCreacion = LocalDate.now();
    }*/
}
