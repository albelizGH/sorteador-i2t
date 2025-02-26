package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.model.CategoriaTope;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoriaTopeMapper {
    CategoriaTopeMapper INSTANCE = Mappers.getMapper(CategoriaTopeMapper.class);

    CategoriaTopeResponseDTO toResponseDTO(CategoriaTope categoriaTope);

    CategoriaTope fromResponseDTO(CategoriaTopeResponseDTO categoriaTopeResponseDTO);

    CategoriaTope fromCreateDTO(CategoriaTopeCreateDTO categoriaTopeCreateDTO);
}
