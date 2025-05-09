package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.CategoriaTopeUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.CategoriaTopeInitialDTO;
import com.pentabyte.projects.sorteador.service.CategoriaTopeService;
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
@RequestMapping("/coordinador/categoria_tope")
@Tag(name = "Categorías topes", description = "Operaciones para gestionar las categorias tope del coordinador.")
public class CategoriaTopeCoordinadorController {

    private final CategoriaTopeService categoriaTopeService;

    @Autowired
    public CategoriaTopeCoordinadorController(CategoriaTopeService categoriaTopeService) {
        this.categoriaTopeService = categoriaTopeService;
    }

    @Operation(
            summary = "Crear una nueva categoría tope",
            description = "Registra una nueva categoría tope en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<CategoriaTopeResponseDTO>> crear(
            @RequestBody @Valid CategoriaTopeCreateDTO categoria) {
        ResponseDTO<CategoriaTopeResponseDTO> response = categoriaTopeService.crear(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Actualizar una categoría tope",
            description = "Actualiza una categoría tope existente en el sistema con los datos proporcionados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoriaTopeResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody @Valid CategoriaTopeUpdateDTO categoriaTopeUpdateDTO) {
        ResponseDTO<CategoriaTopeResponseDTO> response = categoriaTopeService.actualizar(id, categoriaTopeUpdateDTO);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Actualizar una categoría tope",
            description = "Actualiza una categoría tope existente en el sistema con los datos proporcionados."
    )
    @GetMapping()
    public ResponseEntity<ResponseDTO<PaginaDTO<CategoriaTopeInitialDTO>>> getCategoriasTopes(
            @PageableDefault(size = 5) Pageable pageable) {
        ResponseDTO<PaginaDTO<CategoriaTopeInitialDTO>> response = categoriaTopeService.obtenerCategoriasCoordinador(pageable);

        return ResponseEntity.ok(response);
    }


}
