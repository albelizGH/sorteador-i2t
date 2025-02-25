package com.pentabyte.projects.sorteador.dto.request.creacion;

import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;

/*
 * PONER LUEGO LAS VALIDACIONES DE CREACIÓN
 * */
public record CategoriaTopeCreateDTO(CategoriaResponseDTO categoria, Integer cantidadMinima, Integer cantidadMaxima,
                                     Boolean esAutoridad) {
}
