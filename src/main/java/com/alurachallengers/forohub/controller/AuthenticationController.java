package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.model.dtos.AuthLoginRequest;
import com.alurachallengers.forohub.model.dtos.DatosJWTtoken;
import com.alurachallengers.forohub.serviceImpl.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Operation(
            summary = "Login de usuario",
            description = "Permite iniciar sesión con las credenciales del usuario",
            tags = {"Autenticación"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Autenticación de Usuario",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthLoginRequest.class)
                    )
            )
    )
    @PostMapping("/loging")
    public ResponseEntity<DatosJWTtoken> login(
            /*@Parameter(name = "authLoginRequest",required = true,description = "Objeto que contiene las credenciales del usuario")*/
            @RequestBody @Valid AuthLoginRequest authLoginRequest){
        return new ResponseEntity<>(usuarioService.login(authLoginRequest), HttpStatus.OK);
    }
}
