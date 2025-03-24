package com.pentabyte.projects.sorteador.dto.request.creacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record GrupoCreateDTO(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @Min(value = 1, message = "La cantidad mínima tiene que ser mayor a 0")
        @NotNull(message = "El orden de grupo no puede ser nulo")
        Integer ordenDeGrupo,

        @NotNull(message = "El id de categoria no puede ser nulo")
        Long categoriaId,

        @Size(min = 1, message = "Debe haber al menos un integrante.")
         List<Long> integrantesIds

) {
}
