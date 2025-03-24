package com.pentabyte.projects.sorteador.dto.response.initial;

public record CategoriaInitialDTO(
        Long id,
        String nombre,
        Integer maximoAutoridades,
        Integer minimoAutoridades,
        Integer maximoAuxiliares,
        Integer minimoAuxiliares
) {
}

