package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pentabyte.projects.sorteador.model.Estado;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record AsignacionResponseDTO(
        Long id,
        Estado estado,
        GrupoResponseDTO grupo,
        SorteoResponseDTO sorteo
) {
}
