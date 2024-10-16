package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.serviceImpl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping
    public ResponseEntity<List<Usuario>> getUsuarios(){
        return new ResponseEntity<>(usuarioService.getAllUsuarios(), HttpStatus.OK);
    }
    @PostMapping
    public Optional<ResponseEntity<Optional<Usuario>>> crearUsuario(@RequestBody Usuario usuario){
        return Optional.of(new ResponseEntity<>(usuarioService.createUsuario(usuario), HttpStatus.CREATED));
    }
}
