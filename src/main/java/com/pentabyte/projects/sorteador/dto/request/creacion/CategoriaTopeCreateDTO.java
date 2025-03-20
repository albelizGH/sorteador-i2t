package com.pentabyte.projects.sorteador.dto.request.creacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CategoriaTopeCreateDTO(



        @Min(value = 1, message = "La cantidad mínima tiene que ser mayor a 0")
        @NotNull(message = "La cantidad mínima no puede ser nula")
        Integer cantidadMinima,

        @NotNull(message = "La cantidad máxima no puede ser nula")
        @Min(value = 1, message = "La cantidad máxima tiene que ser mayor a 0")
        Integer cantidadMaxima,

        @NotNull(message = "El valor de 'esAutoridad' no puede ser nulo")
        Boolean esAutoridad
) {
}
