package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.response.initial.AsignacionInitialResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ReemplazoInitialResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.SorteoInitialResponseDTO;
import com.pentabyte.projects.sorteador.service.AsignacionService;
import com.pentabyte.projects.sorteador.service.SolicitudDeReemplazoService;
import com.pentabyte.projects.sorteador.service.SorteoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/coordinador/inicial")
public class InitialStateCoordinador {

    private final AsignacionService asignacionService;
    private final SorteoService sorteoService;
    private final SolicitudDeReemplazoService solicitudDeReemplazoService;

    @Autowired
    public InitialStateCoordinador(AsignacionService asignacionService, SorteoService sorteoService, SolicitudDeReemplazoService solicitudDeReemplazoService) {
        this.asignacionService = asignacionService;
        this.sorteoService = sorteoService;
        this.solicitudDeReemplazoService = solicitudDeReemplazoService;
    }


    @Operation(
            summary = "Obtener el estado inicial de asignaciones",
            description = "Obtiene el estado inicial de las asignaciones de los sorteos"
    )
    @GetMapping("/asignaciones")
    public ResponseEntity getAsignaciones(@PageableDefault(size = 5) Pageable pageable, @RequestParam(value = "", required = false) Long grupoId) {
        AsignacionInitialResponseDTO response = this.asignacionService.getInicialAsignacionesCoordinador(pageable, grupoId);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Obtener el estado inicial de sorteos",
            description = "Obtiene el estado inicial de los sorteos"
    )
    @GetMapping("/sorteos")
    public ResponseEntity getSorteos(@PageableDefault(size = 5) Pageable pageable,
                                     @RequestParam(value = "", required = false) Long categoriaId,
                                     @RequestParam(value = "", required = false) LocalDate fechaInicio,
                                     @RequestParam(value = "", required = false) LocalDate fechaFin) {

        LocalDateTime fechaInicioLocalDateTime = fechaInicio != null ? fechaInicio.atStartOfDay() : null;
        LocalDateTime fechaFinLocalDateTime = fechaFin != null ? fechaFin.atStartOfDay() : null;

        SorteoInitialResponseDTO response = this.sorteoService.getInicialSorteosCoordinador(pageable, categoriaId, fechaInicioLocalDateTime, fechaFinLocalDateTime);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Obtener el estado inicial de reemplazos",
            description = "Obtiene el estado inicial de los reemplazos"
    )
    @GetMapping("/reemplazos")
    public ResponseEntity getReemplazo(@PageableDefault(size = 5) Pageable pageable) {
        ReemplazoInitialResponseDTO response = this.solicitudDeReemplazoService.getIncialReemplazosCoordinador(pageable);
        return ResponseEntity.ok(response);
    }
}
