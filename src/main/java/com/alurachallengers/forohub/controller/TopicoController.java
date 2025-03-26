package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.exceptions.TopicoDuplicadoException;
import com.alurachallengers.forohub.model.dtos.RespuestaDTO;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import com.alurachallengers.forohub.service.RespuestaService;
import com.alurachallengers.forohub.service.TopicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/topicos")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private RespuestaService respuestaService;

    @Operation(
            summary = "Obtener los tópicos del foro",
            description = "Permite obtener una lista de los tópicos que publicaron los usuarios",
            tags = {"Tópico"}
    )
    @GetMapping
    public ResponseEntity<List<TopicoDTO>> getTopicos(){
        return new ResponseEntity<>(topicoService.getAllTopicosDTO(), HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener 10 tópicos",
            description = "Permite obtener una lista de hasta 10 tópicos, ordenados por fecha, de manera ascendente",
            tags = {"Tópico"}
    )
    @GetMapping("/teen")
    public ResponseEntity<Page<TopicoDTO>> getTeenTopicosASC(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion){
        return new ResponseEntity<>(topicoService.getAllTopicosDTO(paginacion), HttpStatus.OK);
    }

    @Operation(
            summary = "Obtener un tópico del foro",
            description = "Permite obtener un tópico que publicaron los usuarios",
            tags = {"Tópico"}
    )
    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> getTopico(@PathVariable Long id){
        return topicoService.getTopicoById(id).map(topico -> new ResponseEntity<>(topico, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Crea un tópico en el foro",
            description = "Permite crea un tópico en el foro",
            tags = {"Tópico"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Crea un tópico en el foro",
                    content = @Content(
                            mediaType = "application/json",
                                    schema = @Schema(implementation = TopicoDTO.class)
                    )
            )
    )
    @PostMapping("/{id}")
    public ResponseEntity<TopicoDTO> crearTopico(@PathVariable Long id, @Valid @RequestBody TopicoDTO topicoDTO){
        if (id == null || topicoDTO == null) {
            return ResponseEntity.badRequest().body(null); // 400 Bad Request
        }

        try {
            return new ResponseEntity<>(topicoService.createTopico(id, topicoDTO), HttpStatus.CREATED);
        } catch (TopicoDuplicadoException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /*Este endpoint es para el sub-recurso "Respuesta", ya que este está relacionado directamente con el tópico
    * por ende, es mejor ponerlo en este controller. :3*/
    @Operation(
            summary = "Crea una respuesta a un tópico en el foro",
            description = "Permite crea una respuesta a un tópico en el foro",
            tags = {"Tópico"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Crea una respuesta a un tópico en el foro",
                    content = @Content(
                            mediaType = "application/json",
                                    schema = @Schema(implementation = RespuestaDTO.class)
                    )
            )
    )
    @PostMapping("/{id}/respuestas")
    public ResponseEntity<RespuestaDTO> crearRespuesta(@PathVariable Long id, @Valid @RequestBody RespuestaDTO respuestaDTO){
        if (id == null || respuestaDTO == null) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            return new ResponseEntity<>(respuestaService.createRespuesta(id, respuestaDTO), HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(
            summary = "Actualiza un tópico en el foro",
            description = "Permite actualizar el contenido de un tópico en el foro",
            tags = {"Tópico"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Actualiza un tópico en el foro",
                    content = @Content(
                            mediaType = "application/json",
                                    schema = @Schema(implementation = TopicoDTO.class)
                    )
            )
    )
    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> actualizarTopico(@PathVariable Long id, @Valid @RequestBody TopicoDTO topicoDTO){
        return topicoService.updateTopico(id, topicoDTO).map(topico -> new ResponseEntity<>(topico, HttpStatus.ACCEPTED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(
            summary = "Elimina un tópico en el foro",
            description = "Permite eliminar un tópico en el foro",
            tags = {"Tópico"}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        Optional<TopicoDTO> TopicoDtoActual = topicoService.getTopicoById(id);

        if (TopicoDtoActual.isPresent()) {
            topicoService.deleteTopico(id);
            return new ResponseEntity<>("El tópico fue eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El tópico ingresado no existe", HttpStatus.NOT_FOUND);
    }
}
