package com.pentabyte.projects.sorteador.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Rol {
    AUTORIDAD("Autoridad"),
    AUXILIAR("Auxiliar"),
    CORDINADOR("Coordinador");
    private final String displayRol;

    private Rol(String displayRol) {
            this.displayRol = displayRol;
    }

    @JsonValue
    public String getDisplaySolEstado(){
        return this.displayRol;
    }
    //Metodo para obtener el enum desde un String

    public static Rol fromString(String text){
        for(Rol b : Rol.values()){
            if(b.displayRol.equalsIgnoreCase(text)){
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + text);
    }
}

