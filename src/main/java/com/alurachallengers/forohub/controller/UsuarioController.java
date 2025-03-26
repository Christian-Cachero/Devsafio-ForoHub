package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioAuthDTO;
import com.alurachallengers.forohub.serviceImpl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

/*    @GetMapping
    //@PreAuthorize("permiteAll()")
    public ResponseEntity<List<UsuarioDTO>> getUsuarios(){
        return new ResponseEntity<>(usuarioService.getAllUsuariosDTO(), HttpStatus.OK);
    }*/
    @Operation(
            summary = "creación de usuario",
            description = "Permite registrar a un usuario",
            tags = {"Registro"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Registro de Usuario",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioAuthDTO.class)
                    )
            )
    )
    @PostMapping
    //@PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario){
        return new ResponseEntity<>(usuarioService.createUsuario(usuario), HttpStatus.CREATED);
    }
}
