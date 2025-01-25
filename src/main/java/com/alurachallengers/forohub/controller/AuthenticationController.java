package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.model.dtos.AuthLoginRequest;
import com.alurachallengers.forohub.model.dtos.DatosJWTtoken;
import com.alurachallengers.forohub.service.UsuarioService;
import com.alurachallengers.forohub.serviceImpl.UsuarioServiceImpl;
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

    @PostMapping("/loging")
    public ResponseEntity<DatosJWTtoken> login(@RequestBody @Valid AuthLoginRequest authLoginRequest){
        return new ResponseEntity<>(usuarioService.login(authLoginRequest), HttpStatus.OK);
    }
}
