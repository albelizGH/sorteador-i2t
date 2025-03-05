package com.pentabyte.projects.sorteador.dto.request.creacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SorteoCreateDTO(

        @NotNull(message = "La fecha no puede ser nula")
        LocalDateTime fecha,

        @NotNull(message = "La fecha no puede ser nula")
        Boolean confirmado,

        @NotBlank(message = "El día no puede estar vacío")
        String diaDescriptivo,

        @NotNull(message = "El id de producto no puede ser nulo")
        Long productoId
) {
}
