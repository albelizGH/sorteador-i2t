package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.IntegranteResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.CategoriaInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.IntegranteInitialDTO;
import com.pentabyte.projects.sorteador.service.IntegranteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coordinador/integrantes")
@Tag(name = "Integrantes", description = "Operaciones para gestionar los integrantes desde coordinador.")

public class IntegranteCoordinadorController {

    private final IntegranteService integranteService;

    @Autowired
    public IntegranteCoordinadorController(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<PaginaDTO<IntegranteInitialDTO>>> getIntegranteById(@RequestParam Long id){
        IntegranteInitialDTO integranteDTO = this.integranteService.getIntegranteByIdCoordinador(id);

        ResponseDTO response = new ResponseDTO(integranteDTO,
                new ResponseDTO.EstadoDTO("Integrantes recuperados correctamente", "200"));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sin-grupo")
    public ResponseEntity<List<IntegranteResponseDTO>> obtenerTodosIntegrantesSinGrupo() {
        List<IntegranteResponseDTO> response = integranteService.obtenerTodosIntegrantesSinGrupo();
        return ResponseEntity.ok(response);
    }

}
