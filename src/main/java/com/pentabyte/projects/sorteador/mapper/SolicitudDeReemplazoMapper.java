package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.request.creacion.SolicitudDeReemplazoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.SolicitudDeReemplazoResponseDTO;
import com.pentabyte.projects.sorteador.model.SolicitudDeReemplazo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SolicitudDeReemplazoMapper {
    SolicitudDeReemplazoMapper INSTANCE = Mappers.getMapper(SolicitudDeReemplazoMapper.class);

    SolicitudDeReemplazoResponseDTO toResponseDTO(SolicitudDeReemplazo solicitud);

    SolicitudDeReemplazo fromResponseDTO(SolicitudDeReemplazoResponseDTO solicitud);

    SolicitudDeReemplazo fromCreateDTO(SolicitudDeReemplazoCreateDTO solicitud);

}
