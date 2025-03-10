package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;
import com.pentabyte.projects.sorteador.service.CategoriaService;
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
@RequestMapping("/categorias")
@Tag(name = "Categoría", description = "Endpoints para la gestión de categorías")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Operation(
            summary = "Obtener todas las categorías",
            description = "Devuelve una lista paginada de todas las categorías existentes en el sistema."
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<CategoriaResponseDTO>>> obtenerTodos(
            @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<CategoriaResponseDTO>> response = categoriaService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener una categoría por ID",
            description = "Devuelve la información detallada de una categoría específica según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoriaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<CategoriaResponseDTO> response = categoriaService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear una nueva categoría",
            description = "Registra una nueva categoría en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<CategoriaResponseDTO>> crear(
            @RequestBody @Valid CategoriaCreateDTO categoria) {
        ResponseDTO<CategoriaResponseDTO> response = categoriaService.crear(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
