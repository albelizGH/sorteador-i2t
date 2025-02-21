package com.pentabyte.projects.sorteador.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DiaDescriptivo {
    LUNES("Lunes"),
    MARTES("Martes"),
    MIERCOLES("Miércoles"),
    JUEVES("Jueves"),
    VIERNES("Viernes"),
    SABADO("Sábado"),
    DOMINGO("Domingo");

    private final String displayDia;

    private DiaDescriptivo (String displayDia){
        this.displayDia=displayDia;
    }

    @JsonValue
    public String getDisplayDia(){
        return this.displayDia;
    }

    public static DiaDescriptivo fromString(String dia) {
        for (DiaDescriptivo item : DiaDescriptivo.values()) {
            if (item.displayDia.equalsIgnoreCase(dia)) {
                return item;
            }
        }
        throw new IllegalArgumentException("Valor no encontrado: " + dia);
    }


    }
