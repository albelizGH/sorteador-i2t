package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.GrupoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.GrupoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.GrupoResponseDTO;
import com.pentabyte.projects.sorteador.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/grupos")
@Tag(name = "Grupo", description = "Endpoints para la gestión de grupos")
public class GrupoController {

    private final GrupoService grupoService;

    public GrupoController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @Operation(
            summary = "Obtener todos los grupos",
            description = "Devuelve una lista paginada de todos los grupos existentes en el sistema."
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<GrupoResponseDTO>>> obtenerTodos(
            @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<GrupoResponseDTO>> response = grupoService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener un grupo por ID",
            description = "Devuelve la información detallada de un grupo específico según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<GrupoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<GrupoResponseDTO> response = grupoService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear un nuevo grupo",
            description = "Registra un nuevo grupo en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<GrupoResponseDTO>> crear(
            @RequestBody @Valid GrupoCreateDTO grupoCreateDTO) {
        ResponseDTO<GrupoResponseDTO> response = grupoService.crear(grupoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Agregar integrante a un grupo",
            description = "Se agrega un integrante a un grupo específico segun su ID de integrante y el ID de grupo."
    )
    @PatchMapping("/{grupoId}")
    public ResponseEntity<ResponseDTO<GrupoResponseDTO>> agregarIntegrante(
            @PathVariable Long grupoId, @RequestBody @Valid GrupoUpdateDTO grupoUpdateDTO) {
        ResponseDTO<GrupoResponseDTO> response = grupoService.agregarIntegranteAGrupo(grupoId, grupoUpdateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
