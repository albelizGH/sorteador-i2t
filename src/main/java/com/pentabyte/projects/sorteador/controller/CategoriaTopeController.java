package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.service.CategoriaTopeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categoria-tope")
public class CategoriaTopeController {

    private final CategoriaTopeService categoriaTopeService;

    @Autowired
    public CategoriaTopeController(CategoriaTopeService categoriaTopeService) {
        this.categoriaTopeService = categoriaTopeService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<PaginaDTO<CategoriaTopeResponseDTO>>> obtenerTodos(@PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO<PaginaDTO<CategoriaTopeResponseDTO>> response = categoriaTopeService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoriaTopeResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO<CategoriaTopeResponseDTO> response = categoriaTopeService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<CategoriaTopeResponseDTO>> crear(@RequestBody @Valid CategoriaTopeCreateDTO categoria) {
        ResponseDTO<CategoriaTopeResponseDTO> response = categoriaTopeService.crear(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
