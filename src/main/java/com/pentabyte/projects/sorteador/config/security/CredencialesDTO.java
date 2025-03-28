package com.pentabyte.projects.sorteador.config.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CredencialesDTO(
        @Email
        String email,
        @NotBlank(message = "La contraseña es requerida")
        String password
) {
}
