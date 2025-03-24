package com.pentabyte.projects.sorteador.controller.coordinador;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ProductoInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.SorteoInitialDTO;
import com.pentabyte.projects.sorteador.service.ProductoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coordinador/productos")
@Tag(name = "Productos", description = "Operaciones para gestionar los productos desde coordinador.")

public class ProductoCoordinadorController {

    private final ProductoService productoService;

    public ProductoCoordinadorController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping()
    public ResponseEntity<ResponseDTO<PaginaDTO<ProductoInitialDTO>>> getProductos(@PageableDefault(size = 5) Pageable pageable) {

        PaginaDTO<ProductoInitialDTO> paginaDTO = this.productoService.getProductosCoordinador(pageable);

        ResponseDTO response = new ResponseDTO(paginaDTO,
                new ResponseDTO.EstadoDTO("Sorteos recuperados correctamente", "200"));

        return ResponseEntity.ok(response);
    }


}
