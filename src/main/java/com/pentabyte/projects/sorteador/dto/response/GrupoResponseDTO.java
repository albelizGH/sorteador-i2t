package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GrupoResponseDTO(Long id,
                               String nombre,
                               Integer ordenDeGrupo,
                               CategoriaResponseDTO categoria,
                               List<IntegranteResponseDTO> integrantes) {

}

