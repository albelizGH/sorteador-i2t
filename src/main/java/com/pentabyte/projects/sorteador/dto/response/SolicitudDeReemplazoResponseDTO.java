package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pentabyte.projects.sorteador.model.SolEstado;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SolicitudDeReemplazoResponseDTO(
        Long id,
        String descripcion,
        LocalDate fechaDeSolicitud,
        SolEstado estadoDeSolicitud,
        Long empleadoSolicitanteId,
        Long empleadoReemplazoId,
        Long asignacionDeSolicitanteId,
        Long asignacionDeReemplazoId
) {
}
