package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.SolicitudDeReemplazoResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.SorteoResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ReemplazoInitialDTO;
import com.pentabyte.projects.sorteador.service.SolicitudDeReemplazoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coordinador/reemplazos")
@Tag(name = "Reemplazos coordinador", description = "Operaciones para gestionar los reemplazos de coordinadores.")
public class ReemplazoCoordinadorController {

    private final SolicitudDeReemplazoService solicitudDeReemplazoService;

    public ReemplazoCoordinadorController(SolicitudDeReemplazoService solicitudDeReemplazoService) {
        this.solicitudDeReemplazoService = solicitudDeReemplazoService;
    }


    @Operation(
            summary = "Obtener fechas por solicitante.",
            description = "Recupera una lista paginada de fechas asociadas a un solicitante específico identificado por su ID."
    )
    @GetMapping("/fechasSolicitante/{idSolicitante}")
    public ResponseEntity<ResponseDTO<PaginaDTO<SorteoResponseDTO>>> buscarFechasPorSolicitante(@PathVariable Long idSolicitante, @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<SorteoResponseDTO>> response = solicitudDeReemplazoService.buscarFechasPorSolicitante(idSolicitante, paginacion);

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener fechas para devolución",
            description = "Recupera una lista paginada de fechas asociadas a una devolución específica identificada por su ID, filtrando por el id de un sorteo."
    )
    @GetMapping("/fechasDevolucion/{idDevolucion}")
    public ResponseEntity<ResponseDTO<PaginaDTO<SorteoResponseDTO>>> buscarFechasParaDevolucion(@PathVariable Long idDevolucion, @RequestParam Long SorteoId, @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<SorteoResponseDTO>> response = solicitudDeReemplazoService.buscarFechasParaDevolucion(idDevolucion, SorteoId, paginacion);

        return ResponseEntity.ok(response);
    }


    @Operation(
            summary = "Aceptar solicitud de reemplazo",
            description = "Permite que un usuario reemplazante acepte una solicitud de reemplazo."
    )
    @PutMapping("/aceptar/{id}")
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

    @Operation(
            summary = "Obtener reemplazos pendientes",
            description = "Obtiene una lista paginada de reemplazos pendientes."
    )
    @GetMapping("/pendientes")
    public ResponseEntity getReemplazosPendientes(@PageableDefault(size = 5) Pageable pageable) {
        PaginaDTO<ReemplazoInitialDTO> response = this.solicitudDeReemplazoService.getReemplazosPendientesCoordinador(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener reemplazos no pendientes",
            description = "Obtiene una lista paginada de reemplazos no pendientes."
    )
    @GetMapping("/no_pendientes")
    public ResponseEntity getReemplazosNoPendiente(@PageableDefault(size = 5) Pageable pageable) {
        PaginaDTO<ReemplazoInitialDTO> response = this.solicitudDeReemplazoService.getReemplazosNoPendientesCoordinador(pageable);
        return ResponseEntity.ok(response);
    }


}
