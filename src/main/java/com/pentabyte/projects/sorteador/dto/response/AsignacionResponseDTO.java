package com.pentabyte.projects.sorteador.dto.response;

import com.pentabyte.projects.sorteador.model.Estado;
import lombok.Builder;

@Builder
public record AsignacionResponseDTO(
        Long id,
        Estado estado,
        GrupoResponseDTO grupo,
        SorteoResponseDTO sorteo
) {
}
