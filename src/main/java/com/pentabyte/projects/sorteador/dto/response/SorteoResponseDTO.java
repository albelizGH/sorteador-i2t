package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pentabyte.projects.sorteador.model.DiaDescriptivo;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SorteoResponseDTO(
        Long id,
        LocalDateTime fecha,
        Boolean confirmado,
        DiaDescriptivo diaDescriptivo,
        ProductoResponseDTO producto
) {
}
