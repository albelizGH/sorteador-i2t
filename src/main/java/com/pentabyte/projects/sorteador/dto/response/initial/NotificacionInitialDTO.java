package com.pentabyte.projects.sorteador.dto.response.initial;

import java.time.LocalDate;

public record NotificacionInitialDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDate fecha,
        //FALTA //
) {
}
