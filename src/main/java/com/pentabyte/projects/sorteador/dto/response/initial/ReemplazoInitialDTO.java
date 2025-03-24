package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.model.SolEstado;

import java.time.LocalDate;

public record ReemplazoInitialDTO(
        Long id,
        String nombreEmpleadoSolicitante,
        String nombreEmpleadoReemplazo,
        String descripcion,
        LocalDate fechaSolicitud,
        LocalDate fechaSorteo,
        String nombreSorteo,
        Long autEmpleadoSolicitante,
        Long autEmpleadoReemplazo,
        SolEstado solEstado,
        Long autAsignacionSolicitante,
        Long autAsignacionReemplazo
) {
}
