package com.pentabyte.projects.sorteador.dto.request.actualizacion;

import jakarta.validation.constraints.Min;

/*
 * PONER LUEGO LAS VALIDACIONES DE ACTUALIZACIÓN, SI SON LAS MISMAS QUE CREACION USAR SOLAMENTE UN DTO. SI NO HAY INFORMACION CONFIDENCIAL USAR MAPPERS
 * */
public record CategoriaTopeUpdateDTO(
        @Min(value = 1, message = "La cantidad maxima tiene que ser mayor a 0")
        Integer cantidadMaxima
)
{}
