package com.pentabyte.projects.sorteador.dto.request.creacion;

import jakarta.validation.constraints.NotBlank;

public record UsuarioCreateDTO(

        @NotBlank(message = "El nombre de usuario no puede ser vacío")
        String usuario,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String contrasenia
) {
}
