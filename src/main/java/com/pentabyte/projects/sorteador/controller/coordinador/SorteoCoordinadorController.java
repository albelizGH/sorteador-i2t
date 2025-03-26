package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.SorteoInitialDTO;
import com.pentabyte.projects.sorteador.service.SorteoService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/coordinador/sorteos")
@Tag(name = "Sorteos coordinador", description = "Endpoints para la gestión de los sorteos para el coordinador")
public class SorteoCoordinadorController {

    private final SorteoService sorteoService;


    public SorteoCoordinadorController(SorteoService sorteoService) {
        this.sorteoService = sorteoService;
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<PaginaDTO<SorteoInitialDTO>>> getSorteos(@PageableDefault(size = 5) Pageable pageable,
                                                                               @RequestParam(value = "", required = false) Long categoriaId,
                                                                               @RequestParam(value = "", required = false) LocalDate fechaInicio,
                                                                               @RequestParam(value = "", required = false) LocalDate fechaFin) {

        LocalDateTime fechaInicioLocalDateTime = fechaInicio != null ? fechaInicio.atStartOfDay() : null;
        LocalDateTime fechaFinLocalDateTime = fechaFin != null ? fechaFin.atStartOfDay() : null;


        PaginaDTO<SorteoInitialDTO> paginaDTO = this.sorteoService.getSorteosCoordinador(pageable, categoriaId, fechaInicioLocalDateTime, fechaFinLocalDateTime);
        ResponseDTO response = new ResponseDTO(paginaDTO,
                new ResponseDTO.EstadoDTO("Sorteos recuperados correctamente", "200"));

        return ResponseEntity.ok(response);
    }
}
