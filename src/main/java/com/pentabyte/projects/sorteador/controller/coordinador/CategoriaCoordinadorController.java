package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.CategoriaInitialDTO;
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
@RequestMapping("/coordinador/categorias")
@Tag(name = "Categorias", description = "Operaciones para gestionar las categorias desde coordinador.")

public class CategoriaCoordinadorController {

    private final CategoriaService categoriaService;

    @Autowired
    public CategoriaCoordinadorController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<PaginaDTO<CategoriaInitialDTO>>> getCategorias(@PageableDefault(size = 5) Pageable pageable) {

        PaginaDTO<CategoriaInitialDTO> paginaDTO = this.categoriaService.getCategoriasCoordinador(pageable);

        ResponseDTO response = new ResponseDTO(paginaDTO,
                new ResponseDTO.EstadoDTO("Categorias recuperadas correctamente", "200"));

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear una nueva categoría",
            description = "Registra una nueva categoría en el sistema con los datos proporcionados."
    )
    @PostMapping
    public ResponseEntity<CategoriaInitialDTO> crear(
            @RequestBody @Valid CategoriaCreateDTO categoria) {
        CategoriaInitialDTO response = categoriaService.crearCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
