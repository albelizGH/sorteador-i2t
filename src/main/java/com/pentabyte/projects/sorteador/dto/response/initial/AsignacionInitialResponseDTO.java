package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

public record AsignacionInitialResponseDTO(
        PaginaDTO<AsignacionInitialDTO> asignaciones,
        PaginaDTO<AsignacionInitialDTO> asignacionesBorrador,
        GlobalDTO global
) {
}
