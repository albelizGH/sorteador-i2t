package com.pentabyte.projects.sorteador.service.Implements;

import com.pentabyte.projects.sorteador.dto.request.actualizacion.AutCategoriaTopeUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.AutCategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.AutCategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.service.ICrudService;

import java.util.List;

public class AutCategoriaTopeServiceImpl implements ICrudService<AutCategoriaTopeResponseDTO, Long, AutCategoriaTopeCreateDTO, AutCategoriaTopeUpdateDTO> {

    @Override
    public AutCategoriaTopeResponseDTO create(AutCategoriaTopeCreateDTO autCategoriaTopeCreateDTO) {
        return AutCategoriaTopeResponseDTO.builder().cantidadMaxima(2).cantidadMinima(1).build();
    }

    @Override
    public AutCategoriaTopeResponseDTO update(Long id, AutCategoriaTopeUpdateDTO autCategoriaTopeUpdateDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public AutCategoriaTopeResponseDTO findById(Long aLong) {
        return null;
    }


    @Override
    public List<AutCategoriaTopeResponseDTO> findAll() {
        return List.of();
    }
}
