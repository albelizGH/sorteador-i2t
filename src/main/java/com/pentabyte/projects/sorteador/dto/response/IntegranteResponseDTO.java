package com.pentabyte.projects.sorteador.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pentabyte.projects.sorteador.model.Rol;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record IntegranteResponseDTO(
        Long id,//1//
        String nombre,//2//
        Integer legajo,
        Rol rol,//3//
        Long grupoId
) {
}
