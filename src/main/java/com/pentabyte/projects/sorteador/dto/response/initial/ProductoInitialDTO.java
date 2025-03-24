package com.pentabyte.projects.sorteador.dto.response.initial;

public record ProductoInitialDTO(
        Long id,
        String nombre,
        Integer orden,
        String categoria
) {
}

