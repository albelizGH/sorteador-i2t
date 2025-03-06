package com.pentabyte.projects.sorteador.dto.request.creacion;

import jakarta.validation.constraints.NotBlank;

public record UsuarioCreateDTO(

        @NotBlank(message = "El username no puede ser vacío")
        String username,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String contrasenia
) {
}
