package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

import java.util.List;

public record ReemplazoInitialResponseDTO(
        GlobalDTO global,
        List<ReemplazoInitialDTO> reemplazosPendientes,
        List<ReemplazoInitialDTO> reemplazosNoPendientes,
        PaginaDTO.PaginacionDTO paginacionPendientes,
        PaginaDTO.PaginacionDTO paginacionNoPendientes
) {
}
