package com.pentabyte.projects.sorteador.controller.auxiliar;
import com.pentabyte.projects.sorteador.dto.response.initial.GrupoInitialDTO;
import com.pentabyte.projects.sorteador.service.GrupoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auxiliar/grupos")
@Tag(name = "Grupos auxiliar", description = "Operaciones para gestionar los grupos de auxiliares.")
public class GrupoAuxiliarController {

    private final GrupoService grupoService;

    public GrupoAuxiliarController(GrupoService grupoService) {
        this.grupoService = grupoService;
    }

    @Operation(
            summary="Obtener el grupo de un integrante" ,
            description = "Obtiene el grupo de un integrante en el sistema"
    )
    @GetMapping
    public ResponseEntity<GrupoInitialDTO> getGrupo(@RequestParam Long id){
        GrupoInitialDTO grupoInitialDTO=this.grupoService.getGrupoAuxiliar(id);
        return ResponseEntity.ok(grupoInitialDTO);
    }


}
