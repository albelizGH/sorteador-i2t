package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.request.creacion.UsuarioCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.UsuarioResponseDTO;
import com.pentabyte.projects.sorteador.model.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);

    UsuarioResponseDTO toResponseDTO(Usuario usuario);

    Usuario fromResponseDTO(UsuarioResponseDTO usuarioResponseDTO);

    Usuario fromCreateDTO(UsuarioCreateDTO usuarioCreateDTO);
}
