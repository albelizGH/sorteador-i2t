package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record CategoriaTopeResponseDTO(Long id, CategoriaResponseDTO categoria, Integer cantidadMinima,
                                       Integer cantidadMaxima, Boolean esAutoridad) {
}
