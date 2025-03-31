package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.response.initial.*;
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

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/coordinador/inicial")
public class InitialStateCoordinador {

    private final AsignacionService asignacionService;
    private final GrupoService grupoService;
    private final ProductoService productoService;
    private final SorteoService sorteoService;
    private final SolicitudDeReemplazoService solicitudDeReemplazoService;
    private final CategoriaService categoriaService;
    private final IntegranteService integranteService;

    @Autowired
    public InitialStateCoordinador(AsignacionService asignacionService, GrupoService grupoService, ProductoService productoService, SorteoService sorteoService, SolicitudDeReemplazoService solicitudDeReemplazoService, CategoriaService categoriaService, IntegranteService integranteService) {
        this.asignacionService = asignacionService;
        this.grupoService = grupoService;
        this.productoService = productoService;
        this.sorteoService = sorteoService;
        this.solicitudDeReemplazoService = solicitudDeReemplazoService;
        this.categoriaService = categoriaService;
        this.integranteService = integranteService;
    }


    @Operation(
            summary = "Obtener todos los grupos",
            description = "Obtiene una lista paginada de todos los grupos existentes en el sistema."
    )
    @GetMapping("/grupos")
    public ResponseEntity getGrupos(@PageableDefault(size = 5) Pageable pageable) {
        GrupoInitialResponseDTO response = this.grupoService.getInicialCoordinador(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener todos los productos",
            description = "Obtiene una lista paginada de todos los productos existentes en el sistema."
    )
    @GetMapping("/productos")
    public ResponseEntity getProductos(@PageableDefault(size = 5) Pageable pageable) {
        ProductoInitialResponseDTO response = this.productoService.getInicialCoordinador(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener todas las categorias",
            description = "Obtiene una lista paginada de todas las categorias existentes en el sistema."
    )
    @GetMapping("/categorias")
    public ResponseEntity getCategorias(@PageableDefault(size = 5) Pageable pageable) {
        CategoriaInitialResponseDTO response = this.categoriaService.getInicialCoordinador(pageable);
        return ResponseEntity.ok(response);
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

    @Operation(
            summary = "Obtener el estado inicial de integrante",
            description = "Obtiene el estado inicial de un integrante por su id"
    )
    @GetMapping("/integrantes")
    public ResponseEntity getInicialIntegranteById() {
        IntegranteInitialDTO response = this.integranteService.getInicialIntegranteByIdCoordinador();
        return ResponseEntity.ok(response);
    }


}
