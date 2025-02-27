package com.pentabyte.projects.sorteador.dto.response;

import lombok.Builder;

@Builder

public record GrupoResponseDTO(Long id, String nombre, Integer ordenGrupo, CategoriaResponseDTO categoria) {
}
