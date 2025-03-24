package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

public record SorteoInitialResponseDTO(
    GlobalDTO global,
    PaginaDTO<SorteoInitialDTO> sorteos
) {
}
