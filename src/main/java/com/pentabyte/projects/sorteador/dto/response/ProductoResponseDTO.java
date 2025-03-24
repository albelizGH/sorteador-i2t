package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductoResponseDTO(
        Long id,
        String nombre,
        Integer orden,
        CategoriaResponseDTO categoria
) {
}
