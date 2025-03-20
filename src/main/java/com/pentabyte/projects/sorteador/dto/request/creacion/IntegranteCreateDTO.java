package com.pentabyte.projects.sorteador.dto.request.creacion;

import com.pentabyte.projects.sorteador.model.Rol;
import jakarta.validation.constraints.*;

public record IntegranteCreateDTO(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @NotNull(message = "El legajo es obligatorio")
        @Positive(message = "El legajo debe ser un número positivo")
        @Min(value = 1000, message = "El legajo debe tener al menos 4 dígitos")
        @Max(value = 999999, message = "El legajo no puede tener más de 6 dígitos")
        Integer legajo,

        @NotNull(message = "El rol no puede estar vacío")
        Rol rol,


        Long grupoId
) {
}
