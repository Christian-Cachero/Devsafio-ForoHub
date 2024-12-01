package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioAuthDTO;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;
import com.alurachallengers.forohub.serviceImpl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
//@PreAuthorize("denyAll()")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @GetMapping
    //@PreAuthorize("permiteAll()")
    public ResponseEntity<List<UsuarioDTO>> getUsuarios(){
        return new ResponseEntity<>(usuarioService.getAllUsuariosDTO(), HttpStatus.OK);
    }
    @PostMapping
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody UsuarioAuthDTO usuario){
        return new ResponseEntity<>(usuarioService.createUsuario(usuario), HttpStatus.CREATED);
    }
}
