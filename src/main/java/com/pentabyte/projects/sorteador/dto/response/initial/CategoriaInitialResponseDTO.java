package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

import java.util.List;

public record CategoriaInitialResponseDTO(
        GlobalDTO global,
       PaginaDTO<CategoriaInitialDTO> categorias
) {
}
