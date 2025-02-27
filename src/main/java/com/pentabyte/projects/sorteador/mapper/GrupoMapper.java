package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.response.GrupoResponseDTO;
import com.pentabyte.projects.sorteador.model.Grupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GrupoMapper {
    GrupoMapper INSTANCE = Mappers.getMapper(GrupoMapper.class);

    Grupo fromResponseDTO(GrupoResponseDTO grupoResponseDTO);

    @Mapping(source = "ordenDeGrupo", target = "ordenDeGrupo")
    GrupoResponseDTO toResponseDTO(Grupo grupo);
}
