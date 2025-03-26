package com.pentabyte.projects.sorteador.dto.request.creacion;

import com.pentabyte.projects.sorteador.model.SolEstado;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record SolicitudDeReemplazoCreateDTO(



        @NotEmpty(message = "La descripción no puede estar vacía")
        @Size(max = 255, message = "La descripción no puede exceder los 255 caracteres")
        String descripcion,

        @NotNull(message = "La fecha de solicitud no puede ser nula")
        @FutureOrPresent(message = "La fecha de solicitud no puede ser anterior a este momento")
        LocalDate fechaDeSolicitud,



        @NotNull(message = "El id del empleado solicitante no puede ser nulo")
        Long empleadoSolicitanteId,

        @NotNull(message = "El id del empleado reemplazo no puede ser nulo")
        Long empleadoReemplazoId,

        @NotNull(message = "El id de la asignación del solicitante no puede ser nulo")
        Long asignacionDeSolicitanteId,

        @NotNull(message = "El id de la asignación del reemplazo no puede ser nulo")
        Long asignacionDeReemplazoId

) {
}
