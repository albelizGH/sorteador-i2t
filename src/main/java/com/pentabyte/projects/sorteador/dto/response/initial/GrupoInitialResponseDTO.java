package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

import java.util.List;

public record GrupoInitialResponseDTO(
        GlobalDTO global,
        PaginaDTO.PaginacionDTO paginacion,
        List<GrupoInitialDTO> grupos
) {
}
