package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.model.dtos.RespuestaDTO;
import com.alurachallengers.forohub.service.RespuestaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/respuestas")
public class RespuestaController {

    @Autowired
    private RespuestaService respuestaService;

    @Operation(
            summary = "Obtener las respuestas de los tópicos",
            description = "Permite obtener una lista de las respuestas",
            tags = {"Respuesta"}
    )
    @GetMapping
    public ResponseEntity<List<RespuestaDTO>> getRespuestas(){
        return new ResponseEntity<>(respuestaService.getAllRespuestasDTO(), HttpStatus.OK);
    }
    @Operation(
            summary = "Obtener una respuesta",
            description = "Permite obtener una respuesta de un tópico",
            tags = {"Respuesta"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> getRespuesta(@PathVariable Long id){
        return respuestaService.getRespuestaById(id).map(respuesta -> new ResponseEntity<>(respuesta, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Actualiza una respuesta",
            description = "Permite actualizar el contenido de una respuesta en un tópico",
            tags = {"Respuesta"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Actualiza una respuesta",
                    content = @Content(
                            mediaType = "application/json",
                                    schema = @Schema(implementation = RespuestaDTO.class)
                    )
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<RespuestaDTO> actualizarRespuesta(@PathVariable Long id, @RequestBody RespuestaDTO respuestaDTO) {
        return respuestaService.updateRespuesta(id, respuestaDTO)
                .map(respuesta -> new ResponseEntity<>(respuesta, HttpStatus.ACCEPTED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Elimina una respuesta de un tópico",
            description = "Permite eliminar una respuesta de un tópico",
            tags = {"Respuesta"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarRespuesta(@PathVariable Long id){
        Optional<RespuestaDTO> RespuestaDtoActual = respuestaService.getRespuestaById(id);

        if (RespuestaDtoActual.isPresent()){
            respuestaService.deleteRespuesta(id);
            return new ResponseEntity<>("Respuesta eliminada", HttpStatus.OK);
        }
        return new ResponseEntity<>("Respuesta no encontrada", HttpStatus.NOT_FOUND);
    }
}
