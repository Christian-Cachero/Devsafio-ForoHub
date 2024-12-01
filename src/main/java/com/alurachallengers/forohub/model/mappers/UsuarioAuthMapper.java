package com.alurachallengers.forohub.model.mappers;


import com.alurachallengers.forohub.model.Usuario;
import com.alurachallengers.forohub.model.dtos.UsuarioAuthDTO;
import com.alurachallengers.forohub.model.dtos.UsuarioDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioAuthMapper {

    @Mappings({
            @Mapping(source = "nombre", target = "nombre"),
            @Mapping(source = "email", target = "email"),
            @Mapping(source = "clave", target = "clave")
    })
    Usuario toUsuario(UsuarioAuthDTO usuarioAuthDTO);

    //List<UsuarioAuthDTO> toUsuariosDTOs(List<Usuario> usuarios);
    @InheritInverseConfiguration
    UsuarioDTO toUsuarioAuthDTO(Usuario usuario);
}
