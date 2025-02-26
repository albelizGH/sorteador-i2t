package com.pentabyte.projects.sorteador.controller;

import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.service.CategoriaTopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categoria-tope")
public class CategoriaTopeController {

    private final CategoriaTopeService categoriaTopeService;

    @Autowired
    public CategoriaTopeController(CategoriaTopeService categoriaTopeService) {
        this.categoriaTopeService = categoriaTopeService;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<Page<CategoriaTopeResponseDTO>>> obtenerTodos(@PageableDefault(size = 10, page = 0) Pageable paginacion) {
        ResponseDTO response = categoriaTopeService.obtenerTodos(paginacion);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoriaTopeResponseDTO>> obtenerPorId(@PathVariable Long id) {
        ResponseDTO response = categoriaTopeService.obtenerPorId(id);
        return ResponseEntity.ok(response);
    }
}
