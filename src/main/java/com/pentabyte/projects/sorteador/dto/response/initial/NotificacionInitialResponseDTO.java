package com.pentabyte.projects.sorteador.dto.response.initial;

public record NotificacionInitialResponseDTO(
        GlobalDTO global,
        PaginaDTO<NotificacionInitialDTO> notificaciones,
        Boolean open
) {
}
