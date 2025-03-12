package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.IntegranteCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.IntegranteResponseDTO;
import com.pentabyte.projects.sorteador.service.IntegranteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/integrantes")
@Tag(name = "Integrante", description = "Endpoints para la gestión de integrantes")
public class IntegranteController {

    private final IntegranteService integranteService;

    @Autowired
    public IntegranteController(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    @Operation(
            summary = "Obtener todos los integrantes",
            description = "Devuelve una lista paginada de todos los integrantes existentes en el sistema."
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<IntegranteResponseDTO>>> obtenerTodos(
            @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<IntegranteResponseDTO>> response = integranteService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener un integrante por ID",
            description = "Devuelve la información detallada de un integrante específico según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<IntegranteResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<IntegranteResponseDTO> response = integranteService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear un nuevo integrante",
            description = "Registra un nuevo integrante en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<IntegranteResponseDTO>> crear(
            @RequestBody @Valid IntegranteCreateDTO integrante) {
        ResponseDTO<IntegranteResponseDTO> response = integranteService.crear(integrante);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}