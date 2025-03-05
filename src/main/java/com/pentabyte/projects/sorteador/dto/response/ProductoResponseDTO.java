package com.pentabyte.projects.sorteador.dto.response;

import lombok.Builder;

@Builder
public record ProductoResponseDTO(
        Long id,
        String nombre,
        Integer orden,
        CategoriaResponseDTO categoria
) {
}
