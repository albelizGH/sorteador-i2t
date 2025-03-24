package com.pentabyte.projects.sorteador.dto.response;

import com.pentabyte.projects.sorteador.model.NotificacionTipo;

import java.time.LocalDateTime;

public record NotificacionResponseDTO(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fecha,
        NotificacionTipo tipo
) {
}
