package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.model.Estado;

import java.util.List;

public record AsignacionInitialDTO(
        Long id,
        Long grupoId,
        Long sorteoId,
        String producto,
        Integer orden,
        String fecha,
        Estado estado,
        String grupo,
        List<String> autoridades,
        List<String> auxiliares

) {
}