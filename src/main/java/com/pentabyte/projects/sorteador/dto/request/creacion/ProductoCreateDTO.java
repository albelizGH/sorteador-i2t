package com.pentabyte.projects.sorteador.dto.request.creacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductoCreateDTO(
        @NotBlank(message = "El nombre no puede ser nulo ni vacío")
        String nombre,

        @Min(value = 1, message = "La cantidad mínima tiene que ser mayor a 0")
        @NotNull(message = "La cantidad mínima no puede ser nula")
        Integer orden,

        @NotNull(message = "El id de la categoría no puede ser nulo")
        Long categoriaId
) {
}
