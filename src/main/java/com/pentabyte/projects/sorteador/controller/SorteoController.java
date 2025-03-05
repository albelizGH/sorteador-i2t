package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.SorteoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.SorteoResponseDTO;
import com.pentabyte.projects.sorteador.service.SorteoService;
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
@RequestMapping("/sorteos")
@Tag(name = "Sorteo", description = "Endpoints para la gestión de sorteo")
public class SorteoController {

    private final SorteoService sorteoService;

    @Autowired
    public SorteoController(SorteoService sorteoService) {
        this.sorteoService = sorteoService;
    }


    @Operation(
            summary = "Obtener todas los sorteos",
            description = "Devuelve una lista paginada de todos los sorteos existentes en el sistema."
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<SorteoResponseDTO>>> obtenerTodos(
            @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<SorteoResponseDTO>> response = sorteoService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener un sorteo por ID",
            description = "Devuelve la información detallada de un sorteo específico según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SorteoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<SorteoResponseDTO> response = sorteoService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear un nuevo sorteo",
            description = "Registra un nuevo sorteo en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<SorteoResponseDTO>> crear(
            @RequestBody @Valid SorteoCreateDTO sorteo) {
        ResponseDTO<SorteoResponseDTO> response = sorteoService.crear(sorteo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
