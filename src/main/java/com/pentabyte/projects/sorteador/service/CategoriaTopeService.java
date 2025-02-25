package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.AutCategoriaTopeUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.AutCategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.AutCategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import org.springframework.data.domain.Page;

public class CategoriaTopeService implements CrudServiceInterface<AutCategoriaTopeResponseDTO, Long, AutCategoriaTopeCreateDTO, AutCategoriaTopeUpdateDTO> {


    @Override
    public ResponseDTO<AutCategoriaTopeResponseDTO> crear(AutCategoriaTopeCreateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<AutCategoriaTopeResponseDTO> actualizar(Long id, AutCategoriaTopeUpdateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<AutCategoriaTopeResponseDTO> eliminar(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<AutCategoriaTopeResponseDTO> encontrarPorId(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<Page<AutCategoriaTopeResponseDTO>> obtenerTodos() {
        return null;
    }
}
