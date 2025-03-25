package com.pentabyte.projects.sorteador.controller.auxiliar;
import com.pentabyte.projects.sorteador.service.AsignacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auxiliar/asignaciones")
@Tag(name = "Asignaciones auxiliar", description = "Operaciones para gestionar las asignaciones de auxiliares.")
public class AsignacionAuxiliarController {

    private final AsignacionService asignacionService;

    public AsignacionAuxiliarController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @GetMapping()
    public ResponseEntity getAsignaciones(@PageableDefault(size = 5) Pageable pageable, @RequestParam Long id) {
        return ResponseEntity.ok(this.asignacionService.getAsignacionesPlanificadasAuxiliar(pageable, id));
    }

}
