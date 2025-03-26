package com.alurachallengers.forohub.model.mappers;

import com.alurachallengers.forohub.model.Respuesta;
import com.alurachallengers.forohub.model.Topico;
import com.alurachallengers.forohub.model.dtos.RespuestaDTO;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UsuarioMapper.class, RespuestaMapper.class})
public interface TopicoMapper {

    @Mappings({
            @Mapping(source = "titulo", target = "titulo"),
            @Mapping(source = "mensaje", target = "mensaje"),
            @Mapping(source = "fechaCreacion", target = "fechaCreacion"),
            @Mapping(source = "estado", target = "estado"),
            @Mapping(source = "autor", target = "autor"),
            @Mapping(source = "contenidoRespuesta", target = "respuestas")
    })
    Topico toTopico(TopicoDTO topicoDTO);

    //List<Respuesta> toRespuesta(RespuestaDTO respuestaDTO);
    List<TopicoDTO> toTopicoDTOs(List<Topico> topicos);

    //implementa pageable al mapper, ya que MapStruct por defecto no lo implementa.
    default Page<TopicoDTO> toTopicoDTOs(Page<Topico> pageable) {
        List<TopicoDTO> dtoList = toTopicoDTOs(pageable.getContent());
        return new PageImpl<>(dtoList, pageable.getPageable(), pageable.getTotalElements());
    }

    @InheritInverseConfiguration
    TopicoDTO toTopicoDTO(Topico topico);
}
