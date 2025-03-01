package com.pentabyte.projects.sorteador.mapper;

import com.pentabyte.projects.sorteador.dto.request.creacion.ProductoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.ProductoResponseDTO;
import com.pentabyte.projects.sorteador.model.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoMapper INSTANCE = Mappers.getMapper(ProductoMapper.class);

    ProductoResponseDTO toResponseDTO(Producto producto);

    Producto fromResponseDTO(ProductoResponseDTO productoResponseDTO);

    Producto fromCreateDTO(ProductoCreateDTO productoCreateDTO);
}
