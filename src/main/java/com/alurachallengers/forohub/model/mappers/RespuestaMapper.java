package com.alurachallengers.forohub.model.mappers;

import com.alurachallengers.forohub.model.Respuesta;
import com.alurachallengers.forohub.model.dtos.RespuestaDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public interface RespuestaMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fechaCreacion", target = "fechaCreacion"),
            @Mapping(source = "respondeA", target = "vistaPreviaMensaje"),
            @Mapping(source = "nombreAutor", target = "autor.nombre"),
            @Mapping(source = "contenidoRespuesta", target = "contenidoRespuesta")
    })
    Respuesta toRespuesta(RespuestaDTO respuestaDTO);

    List<RespuestaDTO> toRespuestaDTOs(List<Respuesta> respuestas);

    @InheritInverseConfiguration
    RespuestaDTO toRespuestaDTO(Respuesta respuesta);

}
