package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CategoriaResponseDTO(Long id,
                                   String nombre,
                                   Integer ultimaSemanaDeAsignacion,
                                   LocalDate ultimaFechaDeAsignacion,
                                   Integer semanasAPlanificar) {
}
