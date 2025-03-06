package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
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
@RequestMapping("/categorias-tope")
@Tag(name = "Categoría tope", description = "Endpoints para la gestión de categorías topes")
public class CategoriaTopeController {

    private final CategoriaTopeService categoriaTopeService;

    @Autowired
    public CategoriaTopeController(CategoriaTopeService categoriaTopeService) {
        this.categoriaTopeService = categoriaTopeService;
    }

    @Operation(
            summary = "Obtener todas las categorías topes",
            description = "Devuelve una lista paginada de todas las categorías topes existentes en el sistema."
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<CategoriaTopeResponseDTO>>> obtenerTodos(
            @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<CategoriaTopeResponseDTO>> response = categoriaTopeService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener una categoría tope por ID",
            description = "Devuelve la información detallada de una categoría tope específica según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoriaTopeResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<CategoriaTopeResponseDTO> response = categoriaTopeService.obtenerPorId(id);
        return ResponseEntity.ok(response);
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
}
