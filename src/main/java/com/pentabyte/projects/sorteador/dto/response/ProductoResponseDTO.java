package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ProductoResponseDTO(Long id, String nombre, Integer orden, CategoriaResponseDTO categoria) {
}
