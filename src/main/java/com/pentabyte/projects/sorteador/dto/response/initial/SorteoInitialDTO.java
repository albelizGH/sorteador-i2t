package com.pentabyte.projects.sorteador.dto.response.initial;

import java.time.LocalDate;
import java.util.Date;

public record SorteoInitialDTO(
        Long id,
        ProductoInitialDTO producto,
        LocalDate fecha,
        Boolean confirmado
) {
}
