package com.pentabyte.projects.sorteador.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;

public enum Rol {
    AUTORIDAD("Autoridad"),
    AUXILIAR("Auxiliar"),
    COORDINADOR("Coordinador");

    private final String displayRol;

    private Rol(String displayRol) {
        this.displayRol = displayRol;
    }

    public static Rol fromString(String text) {
        for (Rol b : Rol.values()) {
            if (b.displayRol.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new RecursoNoEncontradoException("Valor no encontrado: " + text);
    }

    @JsonValue
    public String getDisplaySolEstado() {
        return this.displayRol;
    }
}

