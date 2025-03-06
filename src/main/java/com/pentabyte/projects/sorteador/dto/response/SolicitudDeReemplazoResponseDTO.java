package com.pentabyte.projects.sorteador.dto.response;

import com.pentabyte.projects.sorteador.model.SolEstado;

import java.time.LocalDate;

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
