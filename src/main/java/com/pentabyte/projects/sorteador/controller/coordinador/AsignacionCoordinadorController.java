package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.AsignacionCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.AsignacionResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.AsignacionInitialDTO;
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
@RequestMapping("/coordinador/asignaciones")
@Tag(name = "Asignaciones coordinador", description = "Endpoints para la gestión de las asignaciones para el coordinador")
public class AsignacionCoordinadorController {

    private final AsignacionService asignacionService;

    @Autowired
    public AsignacionCoordinadorController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
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
    public ResponseEntity<ResponseDTO<PaginaDTO<AsignacionInitialDTO>>> ejecutarPlanificacion(
            @RequestParam("cantidadDeSemanas") int cantidadDeSemanas) {

        ResponseDTO<PaginaDTO<AsignacionInitialDTO>> response = asignacionService.planificar(cantidadDeSemanas);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener asignaciones planificadas",
            description = "Devuelve una lista paginada de las asignaciones planificadas."
    )
    @GetMapping("/planificadas")
    public ResponseEntity<ResponseDTO<PaginaDTO<AsignacionInitialDTO>>> obtenerPlanificadas(@PageableDefault(size = 5) Pageable pageable, @RequestParam(value = "", required = false) Long grupoId) {

        PaginaDTO<AsignacionInitialDTO> paginaDTO = asignacionService.getAsignacionesPlanificadaPage(pageable, grupoId);

        ResponseDTO response = new ResponseDTO(paginaDTO,
                new ResponseDTO.EstadoDTO("OK", "Asignaciones planificadas obtenidas correctamente"));

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener asignaciones borrador",
            description = "Devuelve una lista paginada de las asignaciones borrador."
    )
    @GetMapping("/borrador")
    public ResponseEntity<ResponseDTO<PaginaDTO<AsignacionResponseDTO>>> obtenerBorrador(@PageableDefault(size = 5) Pageable pageable, @RequestParam(value = "grupoId", required = false) Long grupoId) {

        PaginaDTO<AsignacionInitialDTO> paginaDTO = asignacionService.getAsignacionesBorradorPage(pageable, grupoId);

        ResponseDTO response = new ResponseDTO(paginaDTO,
                new ResponseDTO.EstadoDTO("OK", "Asignaciones borrador obtenidas correctamente"));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/aprobar")
    public ResponseEntity aprobarAsignaciones() {
        this.asignacionService.aprobarAsignaciones();
        return ResponseEntity.ok().build();
    }


}
