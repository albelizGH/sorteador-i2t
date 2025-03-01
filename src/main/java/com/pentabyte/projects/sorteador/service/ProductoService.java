package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.ProductoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.ProductoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.ProductoResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.ProductoMapper;
import com.pentabyte.projects.sorteador.model.Categoria;
import com.pentabyte.projects.sorteador.model.Producto;
import com.pentabyte.projects.sorteador.repository.CategoriaRepository;
import com.pentabyte.projects.sorteador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements CrudServiceInterface<ProductoResponseDTO, Long, ProductoCreateDTO, ProductoUpdateDTO> {
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper productoMapper;

    @Autowired
    public ProductoService(CategoriaRepository categoriaRepository, ProductoMapper productoMapper, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoMapper = productoMapper;
        this.productoRepository = productoRepository;
    }

    @Override
    public ResponseDTO<ProductoResponseDTO> crear(ProductoCreateDTO dto) {
        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + dto.categoriaId()));

        Producto productoDb = productoRepository.save(new Producto(
                null,
                dto.nombre(),
                dto.orden(),
                categoria,
                null
        ));

        ProductoResponseDTO productoResponseDTO = productoMapper.toResponseDTO(productoDb);

        return new ResponseDTO<ProductoResponseDTO>(
                productoResponseDTO,
                new ResponseDTO.EstadoDTO(
                        "Producto creado exitosamente",
                        "201")
        );    }

    @Override
    public ResponseDTO<ProductoResponseDTO> actualizar(Long aLong, ProductoUpdateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<ProductoResponseDTO> eliminar(Long aLong) {
        return null;
    }

    @Override
    public ResponseDTO<ProductoResponseDTO> obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría tope no encontrada con ID: " + id));

        return new ResponseDTO<ProductoResponseDTO>(
                productoMapper.toResponseDTO(producto),
                new ResponseDTO.EstadoDTO("Producto encontrado exitosamente", "200")
        );    }

    @Override
    public ResponseDTO<PaginaDTO<ProductoResponseDTO>> obtenerTodos(Pageable paginacion) {
        Page<ProductoResponseDTO> productoPage = productoRepository.findAll(paginacion)
                .map(item -> productoMapper.toResponseDTO(item));

        return new ResponseDTO<PaginaDTO<ProductoResponseDTO>>(
                new PaginaDTO<>(productoPage),
                new ResponseDTO.EstadoDTO("Lista de productos obtenida exitosamente", "200")
        );    }
}
