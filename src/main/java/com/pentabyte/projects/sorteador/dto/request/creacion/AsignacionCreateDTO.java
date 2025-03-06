package com.pentabyte.projects.sorteador.dto.request.creacion;

import com.pentabyte.projects.sorteador.model.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AsignacionCreateDTO(

        @NotBlank(message = "El nombre no puede estar vacío")
        Estado estado,

        @NotNull(message = "El id de grupoId no puede ser nulo")
        Long grupoId,

        @NotNull(message = "El id de sorteoId no puede ser nulo")
        Long sorteoId
) {
}
