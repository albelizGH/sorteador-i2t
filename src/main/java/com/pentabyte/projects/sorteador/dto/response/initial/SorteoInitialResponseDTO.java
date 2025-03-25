package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

import java.util.List;

public record SorteoInitialResponseDTO(
        GlobalDTO global,
        List<SorteoInitialDTO> sorteos,
        PaginaDTO.PaginacionDTO paginacion
) {
}
