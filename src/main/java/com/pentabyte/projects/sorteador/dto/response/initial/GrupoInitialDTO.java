package com.pentabyte.projects.sorteador.dto.response.initial;

import java.util.List;

public record GrupoInitialDTO(
        Long id,
        String categoria,
        String nombre,
        Integer ordenGrupo,
        List<IntegranteInitialDTO> integranteList
) {
}
