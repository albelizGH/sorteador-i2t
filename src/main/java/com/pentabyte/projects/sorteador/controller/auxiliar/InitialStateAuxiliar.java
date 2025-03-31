package com.pentabyte.projects.sorteador.controller.auxiliar;

import com.pentabyte.projects.sorteador.dto.response.initial.AsignacionInitialResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GrupoInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ReemplazoInitialResponseDTO;
import com.pentabyte.projects.sorteador.service.*;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/auxiliar/inicial")
public class InitialStateAuxiliar {

    private final AsignacionService asignacionService;
    private final GrupoService grupoService;
    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private final SolicitudDeReemplazoService solicitudDeReemplazoService;

    @Autowired
    public InitialStateAuxiliar(AsignacionService asignacionService, GrupoService grupoService, ProductoService productoService, CategoriaService categoriaService, SolicitudDeReemplazoService solicitudDeReemplazoService) {
        this.asignacionService = asignacionService;
        this.grupoService = grupoService;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
        this.solicitudDeReemplazoService = solicitudDeReemplazoService;
    }


    @Operation(
            summary = "Obtener el estado inicial de asignaciones",
            description = "Obtiene el estado inicial de las asignaciones de los sorteos por integrante"
    )
    @GetMapping("/asignaciones")
    public ResponseEntity getAsignaciones(@PageableDefault(size = 5) Pageable pageable) {
        AsignacionInitialResponseDTO response = this.asignacionService.getInicialAsignacionesAuxiliar(pageable);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Obtener el estado inicial de reemplazos",
            description = "Obtiene el estado inicial de los reemplazos de los sorteos por integrante"
    )
    @GetMapping("/reemplazos")
    public ResponseEntity getReemplazos(@PageableDefault(size = 5) Pageable pageable) {
        ReemplazoInitialResponseDTO response = this.solicitudDeReemplazoService.getIncialReemplazosAuxiliar(pageable);
        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Obtener el estado inicial de grupo",
            description = "Obtiene el estado inicial de un de los sorteos por integrante"
    )
    @GetMapping("/grupos")
    public ResponseEntity getGrupo(@RequestParam Long id) {
        List<GrupoInitialDTO> response = this.grupoService.getGrupoAuxiliar(id);
        return ResponseEntity.ok(response);
    }

}





