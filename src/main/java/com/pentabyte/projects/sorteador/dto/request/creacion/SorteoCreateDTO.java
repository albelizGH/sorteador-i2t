package com.pentabyte.projects.sorteador.dto.request.creacion;

import com.pentabyte.projects.sorteador.model.DiaDescriptivo;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record SorteoCreateDTO(

        @NotNull(message = "La fecha no puede ser nula")
        @FutureOrPresent(message = "La fecha de solicitud no puede ser anterior a este momento")
        LocalDateTime fecha,

        @NotNull(message = "La fecha no puede ser nula")
        Boolean confirmado,

        @NotBlank(message = "El día no puede estar vacío")
        DiaDescriptivo diaDescriptivo,

        @NotNull(message = "El id de producto no puede ser nulo")
        Long productoId
) {
}
