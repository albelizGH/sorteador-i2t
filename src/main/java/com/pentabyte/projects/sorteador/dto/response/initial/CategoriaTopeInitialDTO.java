package com.pentabyte.projects.sorteador.dto.response.initial;

public record CategoriaTopeInitialDTO(
        Long id,
        Integer cantidadMinima,
        Integer cantidadMaxima,
        boolean esAutoridad
) {
}
