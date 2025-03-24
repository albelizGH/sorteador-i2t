package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

import java.util.List;

public record ProductoInitialResponseDTO(
        GlobalDTO global,
        List<ProductoInitialDTO> productos,
        PaginaDTO.PaginacionDTO paginacion
) {
}
