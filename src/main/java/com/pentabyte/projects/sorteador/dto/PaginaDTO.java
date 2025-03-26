package com.pentabyte.projects.sorteador.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginaDTO<T>(
        List<T> contenido,
        PaginacionDTO paginacion
) {

    public PaginaDTO(Page<T> page) {
        this(page.getContent(), new PaginacionDTO(
                page.getNumberOfElements(),
                page.getTotalElements()
        ));
    }

    public record PaginacionDTO(
            Integer cantidadDeElementos,
            Long totalDeElementos
    ) {
    }
}
