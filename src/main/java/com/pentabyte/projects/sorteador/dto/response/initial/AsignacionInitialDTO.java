package com.pentabyte.projects.sorteador.dto.response.initial;

import java.util.List;

public record AsignacionInitialDTO(
        Long id,
        Long grupoId,
        Long sorteoId,
        String producto,
        Integer orden,
        String fecha,
        String estado,
        String grupo,
        List<String> autoridades,
        List<String> auxiliares

) {
}