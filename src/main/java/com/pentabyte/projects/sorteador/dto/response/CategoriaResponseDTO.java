package com.pentabyte.projects.sorteador.dto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CategoriaResponseDTO(Long id,
                                   String nombre,
                                   Integer ultimaSemanaDeAsignacion,
                                   LocalDate ultimaFechaDeAsignacion,
                                   Integer semanasAPlanificar) {
}
