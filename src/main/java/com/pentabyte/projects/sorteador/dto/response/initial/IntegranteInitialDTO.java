package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.model.Rol;

public record IntegranteInitialDTO(
        Long id,
        Long autGrupoId,
        String nombre,
        Integer legajo,
        Rol rol,
        Long autUsuarioId
) {
}
