package com.pentabyte.projects.sorteador.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Estado {
    PLANIFICADO("Planificado"),
    BORRADOR("Borrador");
    private final String displayEstado;

    private Estado(String displayEstado) {
        this.displayEstado = displayEstado;
    }

    @JsonValue
    public String getDisplayEstado(){
        return this.displayEstado;
    }
    //Metodo para obtener el enum desde un String

    public static Estado fromString(String text){
        for(Estado b : Estado.values()){
            if(b.displayEstado.equalsIgnoreCase(text)){
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + text);
    }





}

