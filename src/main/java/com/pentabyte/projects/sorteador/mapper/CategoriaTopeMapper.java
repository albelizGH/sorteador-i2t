package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.model.CategoriaTope;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoriaTopeMapper {
    CategoriaTopeMapper INSTANCE = Mappers.getMapper(CategoriaTopeMapper.class);

    CategoriaTopeResponseDTO categoriaTopeToCategoriaTopeResponseDTO(CategoriaTope categoriaTope);

    CategoriaTope categoriaTopeResponseDTOToCategoriaTope(CategoriaTopeResponseDTO categoriaTopeResponseDTO);
}
