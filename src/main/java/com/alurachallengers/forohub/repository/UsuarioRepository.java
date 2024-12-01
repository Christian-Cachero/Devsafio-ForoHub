package com.alurachallengers.forohub.repository;

import com.alurachallengers.forohub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    //List<UsuarioAuthDTO> findByEmailAndContrasena();

}
