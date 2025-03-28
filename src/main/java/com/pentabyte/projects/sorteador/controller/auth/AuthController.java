package com.pentabyte.projects.sorteador.controller.auth;

import com.pentabyte.projects.sorteador.config.security.AuthenticationResponseDTO;
import com.pentabyte.projects.sorteador.config.security.CredencialesDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.IntegranteCreateDTO;
import com.pentabyte.projects.sorteador.model.Integrante;
import com.pentabyte.projects.sorteador.service.IntegranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Endpoints relacionados con la autenticación de usuarios en el sistema")
public class AuthController {

    private final IntegranteService integranteService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthController(IntegranteService integranteService, PasswordEncoder passwordEncoder) {
        this.integranteService = integranteService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(
            summary = "Login de usuario",
            description = "Permite a un usuario autenticarse proporcionando sus credenciales (email y contraseña). " +
                    "Devuelve un token de autenticación si las credenciales son válidas."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDTO> login(@RequestBody @Valid CredencialesDTO credenciales) {
        // Codificando la contraseña usando BCrypt
        String hashedPassword = passwordEncoder.encode(credenciales.password());

        // Imprimiendo la contraseña codificada en consola para ver el resultado
        System.out.println("Contraseña hasheada: " + hashedPassword);

        AuthenticationResponseDTO response = integranteService.login(credenciales);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Login de usuario",
            description = "Permite a un usuario autenticarse proporcionando sus credenciales (email y contraseña). " +
                    "Devuelve un token de autenticación si las credenciales son válidas."
    )
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid IntegranteCreateDTO dto) {
        Integrante usuario = integranteService.registrarUsuario(dto);
        return ResponseEntity.ok(usuario);
    }
}
