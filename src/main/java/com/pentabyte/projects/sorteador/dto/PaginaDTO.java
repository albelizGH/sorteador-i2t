package com.pentabyte.projects.sorteador.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginaDTO<T>(
        List<T> contenido,
        PaginacionDTO paginacion
) {

    public PaginaDTO(Page<T> page) {
        this(page.getContent(), new PaginacionDTO(
                5,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.isLast()
        ));
    }

    public record PaginacionDTO(
            Integer cantidadDeElementos,
            Long totalDeElementos,
            Integer totalDePaginas,
            Integer numeroDePagina,
            Boolean esUltimaPagina
    ) {
    }
}
