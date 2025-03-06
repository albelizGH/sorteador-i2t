package com.pentabyte.projects.sorteador.dto.response;

import lombok.Builder;

@Builder
public record AsignacionResponseDTO(
        Long id,
        String estado,
        GrupoResponseDTO grupo,
        SorteoResponseDTO sorteo
) {
}
