package com.pentabyte.projects.sorteador.entity;

import com.fasterxml.jackson.annotation.JsonValue;

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
        throw new IllegalArgumentException("Valor no encontrado: " + text);
    }

    @JsonValue
    public String getDisplayEstado() {
        return this.displayEstado;
    }


}

