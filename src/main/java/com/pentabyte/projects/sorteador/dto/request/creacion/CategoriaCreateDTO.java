package com.pentabyte.projects.sorteador.dto.request.creacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CategoriaCreateDTO(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "La lista de tope de categorias no puede ser nula")
        List<Long> categoriaTopeIdList

) {
}
