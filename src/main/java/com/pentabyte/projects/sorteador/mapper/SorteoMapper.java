package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.request.creacion.SorteoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.SorteoResponseDTO;
import com.pentabyte.projects.sorteador.model.Sorteo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SorteoMapper {
    SorteoMapper INSTANCE = Mappers.getMapper(SorteoMapper.class);

    SorteoResponseDTO toResponseDTO(Sorteo sorteo);

    Sorteo fromResponseDTO(SorteoResponseDTO sorteoResponseDTO);

    Sorteo fromCreateDTO(SorteoCreateDTO sorteoCreateDTO);
}
