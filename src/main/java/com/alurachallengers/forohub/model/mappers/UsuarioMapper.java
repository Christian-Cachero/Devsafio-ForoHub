package com.alurachallengers.forohub.model.mappers;

import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nombre", target = "nombre")
    })
    Usuario toUsuario(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> toUsuariosDTOs(List<Usuario> usuarios);
    @InheritInverseConfiguration
    UsuarioDTO toUsuarioDTO(Usuario usuario);
}
