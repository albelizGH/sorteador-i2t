package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

import java.util.List;

public record AsignacionInitialResponseDTO(
        List<AsignacionInitialDTO> asignaciones,
        List<AsignacionInitialDTO> asignacionesBorrador,
        GlobalDTO global,
        PaginaDTO.PaginacionDTO paginacion,
        PaginaDTO.PaginacionDTO paginacionBorrador
) {
}
