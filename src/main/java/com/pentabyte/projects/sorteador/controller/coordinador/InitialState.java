package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.response.initial.AsignacionInitialResponseDTO;
import com.pentabyte.projects.sorteador.service.AsignacionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coordinador/inicial")
public class InitialState {

    private final AsignacionService asignacionService;

    @Autowired
    public InitialState(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @Operation(
            summary = "Obtener planificación inicial",
            description = "Obtiene la planificación inicial de las asignaciones de los sorteos"
    )
    @GetMapping("/asignaciones")
    public ResponseEntity getAsignaciones(@PageableDefault(size = 5) Pageable pageable) {
        AsignacionInitialResponseDTO response = this.asignacionService.getInicialCoordinador(pageable);
        return ResponseEntity.ok(response);
    }
}
