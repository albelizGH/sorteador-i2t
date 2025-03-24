package com.pentabyte.projects.sorteador.dto.response.initial;

import lombok.Builder;

@Builder
public record GlobalDTO(Integer totales,
                        Integer planificadas,
                        Integer borrador
) {

}
