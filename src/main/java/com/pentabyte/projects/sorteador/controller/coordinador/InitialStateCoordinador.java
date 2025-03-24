package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.response.initial.*;
import com.pentabyte.projects.sorteador.service.AsignacionService;
import com.pentabyte.projects.sorteador.service.CategoriaService;
import com.pentabyte.projects.sorteador.service.GrupoService;
import com.pentabyte.projects.sorteador.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coordinador/inicial")
public class InitialStateCoordinador {

    private final AsignacionService asignacionService;
    private final GrupoService grupoService;
    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    @Autowired
    public InitialStateCoordinador(AsignacionService asignacionService, GrupoService grupoService, ProductoService productoService, CategoriaService categoriaService) {
        this.asignacionService = asignacionService;
        this.grupoService = grupoService;
        this.productoService = productoService;
        this.categoriaService = categoriaService;
    }

    @Operation(
            summary = "Obtener planificación inicial",
            description = "Obtiene la planificación inicial de las asignaciones de los sorteos"
    )
    @GetMapping("/asignaciones")
    public ResponseEntity getAsignaciones(@PageableDefault(size = 5) Pageable pageable) {
        AsignacionInitialResponseDTO response = this.asignacionService.getInicial(pageable,null);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary="Obtener todos los grupos",
            description="Obtiene una lista paginada de todos los grupos existentes en el sistema."
    )
    @GetMapping("/grupos")
    public ResponseEntity getGrupos(@PageableDefault(size = 5) Pageable pageable){
        GrupoInitialResponseDTO response=this.grupoService.getInicialCoordinador(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary="Obtener todos los productos",
            description = "Obtiene una lista paginada de todos los productos existentes en el sistema."
    )
    @GetMapping("/productos")
    public ResponseEntity getProductos(@PageableDefault(size = 5) Pageable pageable){
        ProductoInitialResponseDTO response=this.productoService.getInicialCoordinador(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary="Obtener todas las categorias",
            description = "Obtiene una lista paginada de todas las categorias existentes en el sistema."
    )
    @GetMapping("/categorias")
    public ResponseEntity getCategorias(@PageableDefault(size = 5) Pageable pageable){
        CategoriaInitialResponseDTO response=this.categoriaService.getInicialCoordinador(pageable);
        return ResponseEntity.ok(response);
    }
}
