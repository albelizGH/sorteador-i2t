package com.pentabyte.projects.sorteador.controller.auxiliar;

import com.pentabyte.projects.sorteador.dto.response.initial.AsignacionInitialResponseDTO;
import com.pentabyte.projects.sorteador.service.AsignacionService;
import com.pentabyte.projects.sorteador.service.CategoriaService;
import com.pentabyte.projects.sorteador.service.GrupoService;
import com.pentabyte.projects.sorteador.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
    @RequestMapping("/auxiliar/inicial")
    public class InitialStateAuxiliar {

        private final AsignacionService asignacionService;
        private final GrupoService grupoService;
        private final ProductoService productoService;
        private final CategoriaService categoriaService;
        @Autowired
        public InitialStateAuxiliar(AsignacionService asignacionService, GrupoService grupoService, ProductoService productoService, CategoriaService categoriaService) {
            this.asignacionService = asignacionService;
            this.grupoService = grupoService;
            this.productoService = productoService;
            this.categoriaService = categoriaService;
        }
        //las asignaciones pero x ID del integrante//
        //los grupos por integrante//






    }
