package com.pentabyte.projects.sorteador.controller.auxiliar;

import com.pentabyte.projects.sorteador.service.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auxiliar/inicial")
public class InitialStateAuxiliar {

    private final AsignacionService asignacionService;

    @Autowired
    public InitialStateAuxiliar(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }


}
