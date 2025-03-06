package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;
import com.pentabyte.projects.sorteador.model.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);

    Categoria fromResponseDTO(CategoriaResponseDTO categoriaResponseDTO);

    CategoriaResponseDTO toResponseDTO(Categoria categoria);

    Categoria fromCreateDTO(CategoriaResponseDTO grupoCreateDTO);
}
