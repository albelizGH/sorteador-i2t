package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.response.AsignacionResponseDTO;
import com.pentabyte.projects.sorteador.model.Asignacion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AsignacionMapper {
    AsignacionMapper INSTANCE = Mappers.getMapper(AsignacionMapper.class);

    Asignacion fromResponseDTO(AsignacionResponseDTO asignacionResponseDTO);

    AsignacionResponseDTO toResponseDTO(Asignacion asignacion);
}
