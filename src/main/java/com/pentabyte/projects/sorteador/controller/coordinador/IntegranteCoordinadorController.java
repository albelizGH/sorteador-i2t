package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.CategoriaInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.IntegranteInitialDTO;
import com.pentabyte.projects.sorteador.service.IntegranteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coordinador/integrantes")
@Tag(name = "Integrantes", description = "Operaciones para gestionar los integrantes desde coordinador.")

public class IntegranteCoordinadorController {

    private final IntegranteService integranteService;

    @Autowired
    public IntegranteCoordinadorController(IntegranteService integranteService) {
        this.integranteService = integranteService;
    }

//    @GetMapping()
//    public ResponseEntity<ResponseDTO<PaginaDTO<IntegranteInitialDTO>>> getIntegrantes(@PageableDefault(size = 5) Pageable pageable){
//        PaginaDTO<IntegranteInitialDTO> paginaDTO = this.integranteService.getIntegrantesCoordinador(pageable);
//
//        ResponseDTO response = new ResponseDTO(paginaDTO,
//                new ResponseDTO.EstadoDTO("Integrantes recuperados correctamente", "200"));
//
//        return ResponseEntity.ok(response);
//    }

}
