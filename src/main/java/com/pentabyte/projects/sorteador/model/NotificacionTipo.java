package com.pentabyte.projects.sorteador.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;

public enum NotificacionTipo {
    REEMPLAZOS("Reemplazos"),
    GRUPOS("Grupos"),
    ASIGNACIONES("Asignaciones");

    private final String displayNotificacion;

    private NotificacionTipo(String displayNotificacion) {
        this.displayNotificacion = displayNotificacion;
    }

    public NotificacionTipo fromString(String text) {
        for (NotificacionTipo b : NotificacionTipo.values()) {
            if (b.getDisplayNotificacion().equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new RecursoNoEncontradoException("Valor no encontrado: " + text);
    }

    @JsonValue
    public String getDisplayNotificacion() {
        return this.displayNotificacion;
    }


}
