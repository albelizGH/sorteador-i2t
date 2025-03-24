package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.AsignacionCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.AsignacionResponseDTO;
import com.pentabyte.projects.sorteador.service.AsignacionService;
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
@RequestMapping("/asignaciones")
@Tag(name = "Asignación", description = "Endpoints para la gestión de asignaciones")
public class AsignacionController {

    private final AsignacionService asignacionService;

    @Autowired
    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @Operation(
            summary = "Obtener todas las asignaciones",
            description = "Devuelve una lista paginada de todas las asignaciones existentes en el sistema."
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<AsignacionResponseDTO>>> obtenerTodos(
            @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<AsignacionResponseDTO>> response = asignacionService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener una asignación por ID",
            description = "Devuelve la información detallada de una asignación específica según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<AsignacionResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<AsignacionResponseDTO> response = asignacionService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear una nueva asignación",
            description = "Registra una nueva asignación en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<AsignacionResponseDTO>> crear(
            @RequestBody @Valid AsignacionCreateDTO asignacion) {
        ResponseDTO<AsignacionResponseDTO> response = asignacionService.crear(asignacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Generar planificación automática",
            description = "Genera una planificación automática para las semanas requeridas."
    )
    @GetMapping("/planificar")
    public ResponseEntity<ResponseDTO<PaginaDTO<AsignacionResponseDTO>>> ejecutarPlanificacion(
            @RequestParam("cantidadDeSemanas") int cantidadDeSemanas) {
        ResponseDTO<PaginaDTO<AsignacionResponseDTO>> response = asignacionService.planificar(cantidadDeSemanas);
        return ResponseEntity.ok(response);
    }

}