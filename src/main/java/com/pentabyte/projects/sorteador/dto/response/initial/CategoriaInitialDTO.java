package com.pentabyte.projects.sorteador.dto.response.initial;

import java.util.List;

public record CategoriaInitialDTO(
        Long id,
        String nombre,
        List<CategoriaTopeInitialDTO> categoriaTopeList
) {
}
