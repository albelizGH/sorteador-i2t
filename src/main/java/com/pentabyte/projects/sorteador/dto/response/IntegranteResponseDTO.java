package com.pentabyte.projects.sorteador.dto.response;

import com.pentabyte.projects.sorteador.model.Rol;
import lombok.Builder;

@Builder
public record IntegranteResponseDTO(
        Long id,
        String nombre,
        Integer legajo,
        Rol rol,
        Long grupoId
) {
}
