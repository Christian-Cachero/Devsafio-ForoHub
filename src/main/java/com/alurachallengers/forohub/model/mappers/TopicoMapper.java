package com.alurachallengers.forohub.model.mappers;

import com.alurachallengers.forohub.model.Topico;
import com.alurachallengers.forohub.model.dtos.TopicoDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TopicoMapper {

    @Mappings({
            @Mapping(source = "titulo", target = "titulo"),
            @Mapping(source = "mensaje", target = "mensaje"),
            @Mapping(source = "autor", target = "autor")
    })
    Topico toTopico(TopicoDTO topicoDTO);

    @InheritInverseConfiguration
    TopicoDTO toTopicoDTO(Topico topico);
}
