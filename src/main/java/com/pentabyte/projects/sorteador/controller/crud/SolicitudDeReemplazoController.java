package com.pentabyte.projects.sorteador.controller.crud;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.SolicitudDeReemplazoResponseDTO;
import com.pentabyte.projects.sorteador.service.SolicitudDeReemplazoService;
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

}
