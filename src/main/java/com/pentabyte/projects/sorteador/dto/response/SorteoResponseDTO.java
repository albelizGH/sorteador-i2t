package com.pentabyte.projects.sorteador.dto.response;

import com.pentabyte.projects.sorteador.model.DiaDescriptivo;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SorteoResponseDTO(
        Long id,
        LocalDateTime fecha,
        Boolean confirmado,
        DiaDescriptivo diaDescriptivo,
        ProductoResponseDTO producto
) {
}
