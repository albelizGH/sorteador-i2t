package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.response.GrupoResponseDTO;
import com.pentabyte.projects.sorteador.model.Grupo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GrupoMapper {
    GrupoMapper INSTANCE = Mappers.getMapper(GrupoMapper.class);

    GrupoResponseDTO toResponseDTO(Grupo grupo);
    
    Grupo fromResponseDTO(GrupoResponseDTO grupoResponseDTO);

    Grupo fromCreateDTO(GrupoCreateDTO grupoCreateDTO);

}
