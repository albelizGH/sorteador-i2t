package com.pentabyte.projects.sorteador.dto.response.initial;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;

public record ReemplazoInitialResponseDTO(
        GlobalDTO global,
        PaginaDTO<ReemplazoInitialDTO> reemplazosPendientes,
        PaginaDTO<ReemplazoInitialDTO> reemplazosNoPendientes
) {
}
