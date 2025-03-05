package com.pentabyte.projects.sorteador.dto.response;

import lombok.Builder;

@Builder
public record CategoriaTopeResponseDTO(
        Long id,
        CategoriaResponseDTO categoria,
        Integer cantidadMinima,
        Integer cantidadMaxima,
        Boolean esAutoridad
) {
}
