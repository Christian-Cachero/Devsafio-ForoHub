package com.alurachallengers.forohub.controller;

import com.alurachallengers.forohub.exceptions.TopicoDuplicadoException;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import com.alurachallengers.forohub.service.TopicoService;
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

    @GetMapping
    public ResponseEntity<List<TopicoDTO>> getTopicos(){
        return new ResponseEntity<>(topicoService.getAllTopicosDTO(), HttpStatus.OK);
    }

    @GetMapping("/teen")
    public ResponseEntity<Page<TopicoDTO>> getTeenTopicosASC(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion){
        return new ResponseEntity<>(topicoService.getAllTopicosDTO(paginacion), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicoDTO> getTopico(@PathVariable Long id){
        return topicoService.getTopicoById(id).map(topico -> new ResponseEntity<>(topico, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

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
    @PutMapping("/{id}")
    public ResponseEntity<TopicoDTO> actualizarTopico(@PathVariable Long id, @Valid @RequestBody TopicoDTO topicoDTO){
        return topicoService.updateTopico(id, topicoDTO).map(topico -> new ResponseEntity<>(topico, HttpStatus.ACCEPTED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTopico(@PathVariable Long id) {
        Optional<TopicoDTO> currentTopicoDto = topicoService.getTopicoById(id);

        if (currentTopicoDto.isPresent()) {
            topicoService.deleteTopico(id);
            return new ResponseEntity<>("El topico fue eliminado correctamente", HttpStatus.OK);
        }
        return new ResponseEntity<>("El topico ingresado no existe", HttpStatus.NOT_FOUND);
    }
}
