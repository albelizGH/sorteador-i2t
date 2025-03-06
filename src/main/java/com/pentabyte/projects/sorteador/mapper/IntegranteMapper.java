package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.request.creacion.IntegranteCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.IntegranteResponseDTO;
import com.pentabyte.projects.sorteador.model.Integrante;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IntegranteMapper {
    IntegranteMapper INSTANCE = Mappers.getMapper(IntegranteMapper.class);

    IntegranteResponseDTO toResponseDTO(Integrante integrante);

    Integrante fromResponseDTO(IntegranteResponseDTO integranteResponseDTO);

    Integrante fromCreateDTO(IntegranteCreateDTO integranteCreateDTO);
}
