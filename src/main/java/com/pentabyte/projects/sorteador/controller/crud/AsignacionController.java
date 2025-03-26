package com.pentabyte.projects.sorteador.controller.crud;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.AsignacionResponseDTO;
import com.pentabyte.projects.sorteador.service.AsignacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}