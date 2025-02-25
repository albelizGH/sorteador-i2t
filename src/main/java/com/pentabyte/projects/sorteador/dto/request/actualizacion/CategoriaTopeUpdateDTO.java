package com.pentabyte.projects.sorteador.dto.request.actualizacion;

import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;

/*
 * PONER LUEGO LAS VALIDACIONES DE ACTUALIZACIÓN, SI SON LAS MISMAS QUE CREACION USAR SOLAMENTE UN DTO. SI NO HAY INFORMACION CONFIDENCIAL USAR MAPPERS
 * */
public record CategoriaTopeUpdateDTO(CategoriaResponseDTO categoria, Integer cantidadMinima, Integer cantidadMaxima,
                                     Boolean esAutoridad) {
}
