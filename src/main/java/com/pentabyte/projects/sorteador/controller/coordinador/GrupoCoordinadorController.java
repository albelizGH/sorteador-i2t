package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.GrupoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.GrupoResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GrupoInitialDTO;
import com.pentabyte.projects.sorteador.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coordinador/grupos")
@Tag(name = "Grupos", description = "Operaciones para gestionar los grupos desde coordinador.")
public class GrupoCoordinadorController {

    private final GrupoService grupoService;

    @Autowired
    public GrupoCoordinadorController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<PaginaDTO<GrupoInitialDTO>>> getGrupos(Pageable pageable) {
        PaginaDTO<GrupoInitialDTO> paginaDTO = this.grupoService.getGruposCoordinador(pageable);
        ResponseDTO response = new ResponseDTO(paginaDTO,
                new ResponseDTO.EstadoDTO("Grupos recuperados correctamente", "200"));
        return ResponseEntity.ok(response);

    }

    @Operation(
            summary = "Crear un nuevo grupo",
            description = "Registra un nuevo grupo en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<GrupoResponseDTO>> crear(
            @RequestBody @Valid GrupoCreateDTO grupoCreateDTO) {
        ResponseDTO<GrupoResponseDTO> response = grupoService.crear(grupoCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
