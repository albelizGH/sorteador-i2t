package com.pentabyte.projects.sorteador.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
//El Enum debe ir siempre en mayusuclas sus valores//
public enum SolEstado {
   PENDIENTE("Pendiente"),//Como lo va a ver el frontend//
    APROBADA("Aprobada"),
    CANCELADA("Cancelada");

   private final String displaySolEstado;

    private SolEstado(String displaySolEstado) {
         this.displaySolEstado = displaySolEstado;
    }

    @JsonValue
    public String getDisplaySolEstado(){
        return this.displaySolEstado;
    }
    //Metodo para obtener el enum desde un String

    public static SolEstado fromString(String text){
        for(SolEstado b : SolEstado.values()){
            if(b.displaySolEstado.equalsIgnoreCase(text)){
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + text);
    }
}

