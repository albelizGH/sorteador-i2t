package com.pentabyte.projects.sorteador.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;

public enum Estado {
    PLANIFICADO("Planificado"), BORRADOR("Borrador");

    private final String displayEstado;

    private Estado(String displayEstado) {
        this.displayEstado = displayEstado;
    }

    public static Estado fromString(String text) {
        for (Estado b : Estado.values()) {
            if (b.displayEstado.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new RecursoNoEncontradoException("Valor no encontrado: " + text);
    }

    @JsonValue
    public String getDisplayEstado() {
        return this.displayEstado;
    }


}

