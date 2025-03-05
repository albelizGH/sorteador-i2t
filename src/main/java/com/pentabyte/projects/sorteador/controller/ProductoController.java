package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.ProductoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.ProductoResponseDTO;
import com.pentabyte.projects.sorteador.service.ProductoService;
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
@RequestMapping("/productos")
@Tag(name = "Producto", description = "Endpoints para la gestión de productos")
public class ProductoController {
    private final ProductoService productoService;

    @Autowired
    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve una lista paginada de todos los productos existentes en el sistema."
    )
    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<ProductoResponseDTO>>> obtenerTodos(
            @PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<ProductoResponseDTO>> response = productoService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener un producto por ID",
            description = "Devuelve la información detallada de un producto específico según su ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<ProductoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<ProductoResponseDTO> response = productoService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear un nuevo producto",
            description = "Registra un nuevo producto en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<ResponseDTO<ProductoResponseDTO>> crear(
            @RequestBody @Valid ProductoCreateDTO producto) {
        ResponseDTO<ProductoResponseDTO> response = productoService.crear(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
