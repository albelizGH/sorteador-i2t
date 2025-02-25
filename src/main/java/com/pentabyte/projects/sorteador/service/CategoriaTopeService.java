package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.CategoriaTopeUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import org.springframework.data.domain.Page;

public class CategoriaTopeService implements CrudServiceInterface<CategoriaTopeResponseDTO, Long, CategoriaTopeCreateDTO, CategoriaTopeUpdateDTO> {


    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> crear(CategoriaTopeCreateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> actualizar(Long id, CategoriaTopeUpdateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> eliminar(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> encontrarPorId(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<Page<CategoriaTopeResponseDTO>> obtenerTodos() {
        return null;
    }
}
