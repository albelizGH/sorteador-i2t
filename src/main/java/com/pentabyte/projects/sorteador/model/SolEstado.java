package com.pentabyte.projects.sorteador.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;

public enum SolEstado {
    PENDIENTE("Pendiente"),
    APROBADA("Aprobada"),
    CANCELADA("Cancelada"),
    RECHAZADA("Rechazada");

    private final String displaySolEstado;

    private SolEstado(String displaySolEstado) {
        this.displaySolEstado = displaySolEstado;
    }

    public static SolEstado fromString(String text) {
        for (SolEstado b : SolEstado.values()) {
            if (b.displaySolEstado.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new RecursoNoEncontradoException("Valor no encontrado: " + text);
    }

    @JsonValue
    public String getDisplaySolEstado() {
        return this.displaySolEstado;
    }
}

