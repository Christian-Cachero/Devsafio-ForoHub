package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.exceptions.TopicoDuplicadoException;
import com.alurachallengers.forohub.model.Topico;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import com.alurachallengers.forohub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    public ResponseEntity<List<Topico>> getTopicos(){
        return new ResponseEntity<>(topicoService.getAllTopicos(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TopicoDTO> crearTopico(@RequestParam Long autorID, @Valid @RequestBody TopicoDTO topicoDTO){
        if (autorID == null || topicoDTO == null) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }

        try {
            return new ResponseEntity<>(topicoService.createTopico(autorID, topicoDTO), HttpStatus.CREATED);
        } catch (TopicoDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
