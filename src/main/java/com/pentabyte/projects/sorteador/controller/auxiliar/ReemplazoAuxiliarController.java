package com.pentabyte.projects.sorteador.controller.auxiliar;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.SolicitudDeReemplazoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ReemplazoInitialDTO;
import com.pentabyte.projects.sorteador.service.SolicitudDeReemplazoService;
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
@RequestMapping("/auxiliar/reemplazos")
@Tag(name = "Reemplazos auxiliar", description = "Operaciones para gestionar los reemplazos de auxiliares.")

public class ReemplazoAuxiliarController {

    private final SolicitudDeReemplazoService solicitudDeReemplazoService;

    @Autowired
    public ReemplazoAuxiliarController(SolicitudDeReemplazoService solicitudDeReemplazoService) {
        this.solicitudDeReemplazoService = solicitudDeReemplazoService;
    }

    @Operation(
            summary = "Crear una nueva solicitud de reemplazo",
            description = "Registra una nueva solicitud de reemplazo en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<ReemplazoInitialDTO>> crear(
            @RequestBody @Valid SolicitudDeReemplazoCreateDTO solicitudDeReemplazo) {
        ResponseDTO<ReemplazoInitialDTO> response = solicitudDeReemplazoService.crearSolicitud(solicitudDeReemplazo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Obtener todos los reemplazos pendientes",
            description = "Obtiene una lista paginada de todos los reemplazos pendientes existentes en el sistema."
    )
    @GetMapping("/pendientes")
    public ResponseEntity obtenerTodosPendientes(@PageableDefault(size = 5) Pageable pageable, @RequestParam Long id) {
        PaginaDTO<ReemplazoInitialDTO> response = solicitudDeReemplazoService.getReemplazosPendientesAuxiliar(pageable, id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener todos los reemplazos no pendientes",
            description = "Obtiene una lista paginada de todos los reemplazos no pendientes existentes en el sistema."
    )
    @GetMapping("/no-pendientes")
    public ResponseEntity obtenerTodosNoPendientes(@PageableDefault(size = 5) Pageable pageable, @RequestParam Long id) {
        PaginaDTO<ReemplazoInitialDTO> response = solicitudDeReemplazoService.getReemplazosNoPendientesAuxiliar(pageable, id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Eliminar una solicitud de reemplazo",
            description = "Elimina una solicitud de reemplazo especifica"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity eliminar(@PathVariable Long id) {
        solicitudDeReemplazoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }


}
