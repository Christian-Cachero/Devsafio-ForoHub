package com.alurachallengers.forohub.repository;

import com.alurachallengers.forohub.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    @Query("SELECT t FROM Topico t WHERE t.titulo = :titulo AND t.mensaje = :mensaje")
    Optional<Topico> findByTituloAndMensaje(@Param("titulo") String titulo,@Param("mensaje") String mensaje);
}
