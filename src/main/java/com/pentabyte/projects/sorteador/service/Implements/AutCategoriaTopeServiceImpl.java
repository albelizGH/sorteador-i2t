package com.pentabyte.projects.sorteador.service.Implements;

import com.pentabyte.projects.sorteador.dto.request.actualizacion.AutCategoriaTopeUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.AutCategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.AutCategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.service.ICrudService;

import java.util.List;
import java.util.Optional;

public class AutCategoriaTopeServiceImpl implements ICrudService<AutCategoriaTopeResponseDTO, Long, AutCategoriaTopeCreateDTO, AutCategoriaTopeUpdateDTO> {

    @Override
    public AutCategoriaTopeResponseDTO create(AutCategoriaTopeCreateDTO autCategoriaTopeCreateDTO) {
        return null;
    }

    @Override
    public AutCategoriaTopeResponseDTO update(Long id, AutCategoriaTopeUpdateDTO autCategoriaTopeUpdateDTO) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Optional<AutCategoriaTopeResponseDTO> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<AutCategoriaTopeResponseDTO> findAll() {
        return List.of();
    }
}
