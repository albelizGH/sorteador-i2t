package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.SolicitudDeReemplazoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.SolicitudDeReemplazoResponseDTO;
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
@RequestMapping("/solicitudes-reemplazo")
@Tag(name = "Solicitud de Reemplazo", description = "Endpoints para la gestión de solicitudes de reemplazo")
public class SolicitudDeReemplazoController {

    private final SolicitudDeReemplazoService solicitudDeReemplazoService;

    @Autowired
    public SolicitudDeReemplazoController(SolicitudDeReemplazoService solicitudDeReemplazoService) {
        this.solicitudDeReemplazoService = solicitudDeReemplazoService;
    }

    @Operation(
            summary = "Obtener todas las solicitudes de reemplazo",
            description = "Devuelve una lista paginada de todas las solicitudes de reemplazo existentes en el sistema."
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<SolicitudDeReemplazoResponseDTO>>> obtenerTodos(
            @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<SolicitudDeReemplazoResponseDTO>> response = solicitudDeReemplazoService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener una solicitud de reemplazo por ID",
            description = "Devuelve la información detallada de una solicitud de reemplazo específica según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<SolicitudDeReemplazoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<SolicitudDeReemplazoResponseDTO> response = solicitudDeReemplazoService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear una nueva solicitud de reemplazo",
            description = "Registra una nueva solicitud de reemplazo en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<SolicitudDeReemplazoResponseDTO>> crear(
            @RequestBody @Valid SolicitudDeReemplazoCreateDTO solicitudDeReemplazo) {
        ResponseDTO<SolicitudDeReemplazoResponseDTO> response = solicitudDeReemplazoService.crear(solicitudDeReemplazo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Aceptar solicitud de reemplazo",
            description = "Permite que un usuario reemplazante acepte una solicitud de reemplazo."
    )
    @PutMapping("/acpetar/{id}")
    public ResponseEntity<ResponseDTO<SolicitudDeReemplazoResponseDTO>> aceptarSolicitud(@PathVariable Long id, @RequestParam Long usuarioReemplazanteId) {
        ResponseDTO<SolicitudDeReemplazoResponseDTO> response = solicitudDeReemplazoService.aceptarSolicitud(id, usuarioReemplazanteId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Rechazar solicitud de reemplazo",
            description = "Permite que un usuario reemplazante rechaze una solicitud de reemplazo."
    )
    @PutMapping("/rechazar/{id}")
    public ResponseEntity<ResponseDTO<SolicitudDeReemplazoResponseDTO>> rechazarSolicitud(@PathVariable Long id) {
        ResponseDTO<SolicitudDeReemplazoResponseDTO> response = solicitudDeReemplazoService.rechazarSolicitud(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Aprobar solicitud de reemplazo",
            description = "Permite que un coordinador apruebeuna una solicitud de reemplazo."
    )
    @PutMapping("/aprobar/{id}")
    public ResponseEntity<ResponseDTO<SolicitudDeReemplazoResponseDTO>> aprobarSolicitud(@PathVariable Long id) {
        ResponseDTO<SolicitudDeReemplazoResponseDTO> response = solicitudDeReemplazoService.aprobarSolicitud(id);
        return ResponseEntity.ok(response);
    }
}
