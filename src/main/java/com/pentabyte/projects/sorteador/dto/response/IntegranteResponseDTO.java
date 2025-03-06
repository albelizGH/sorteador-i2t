package com.pentabyte.projects.sorteador.dto.response;

import com.pentabyte.projects.sorteador.model.Rol;

public record IntegranteResponseDTO(
        Long id,
        String nombre,
        Integer legajo,
        Rol rol,
        Long grupoId
) {
}
