package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.model.Usuario;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public void login(@RequestBody Usuario usuario){

    }
}
