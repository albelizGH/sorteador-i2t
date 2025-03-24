package com.pentabyte.projects.sorteador.dto.response.initial;

import java.time.LocalDateTime;

public record SorteoInitialDTO(
        Long id,
        ProductoInitialDTO producto,
        LocalDateTime fecha,
        Boolean confirmado
) {
}
