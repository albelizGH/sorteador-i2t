package com.pentabyte.projects.sorteador.dto.request.creacion;

import com.pentabyte.projects.sorteador.dto.response.AutCategoriaResponseDTO;

/*
 * PONER LUEGO LAS VALIDACIONES DE CREACIÓN
 * */
public record AutCategoriaTopeCreateDTO(AutCategoriaResponseDTO autCategoria, Integer cantidadMinima,
                                        Integer cantidadMaxima, Boolean esAutoridad) {
}
