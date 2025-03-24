package com.pentabyte.projects.sorteador.dto.response.initial;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GlobalDTO(Integer totales,
                        Integer planificadas,
                        Integer borrador,
                        Integer pendientes,
                        Integer noPendientes,
                        Integer confirmados,
                        Integer noConfirmados
) {

}