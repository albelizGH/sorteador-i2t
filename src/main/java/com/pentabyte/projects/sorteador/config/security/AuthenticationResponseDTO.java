package com.pentabyte.projects.sorteador.config.security;

import com.pentabyte.projects.sorteador.model.Rol;

public record AuthenticationResponseDTO(
        String jwtToken,
        Rol rol
) {
}
