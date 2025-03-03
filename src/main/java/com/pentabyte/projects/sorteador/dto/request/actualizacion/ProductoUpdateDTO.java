package com.pentabyte.projects.sorteador.dto.request.actualizacion;

import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;

public record ProductoUpdateDTO(String nombre, Integer orden, CategoriaResponseDTO categoria) {
}
