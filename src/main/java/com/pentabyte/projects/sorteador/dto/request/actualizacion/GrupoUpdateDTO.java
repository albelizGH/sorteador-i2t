package com.pentabyte.projects.sorteador.dto.request.actualizacion;
import jakarta.validation.constraints.Size;

import java.util.List;

public record GrupoUpdateDTO(

//        @Size(min = 1, message = "Debe haber al menos un integrantes.")
        List<Long> integrantesIds
) {
}
