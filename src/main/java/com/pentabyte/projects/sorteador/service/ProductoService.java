package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.ProductoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.ProductoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.ProductoResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GlobalDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ProductoInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ProductoInitialResponseDTO;
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

/**
 * Servicio que gestiona la lógica de negocio de los productos.
 * Proporciona operaciones CRUD para la entidad {@link Producto}.
 */
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

    /**
     * Crea un nuevo producto en la base de datos.
     *
     * @param dto DTO con los datos necesarios para crear el producto.
     * @return {@link ResponseDTO} con la información del producto creado.
     * @throws RecursoNoEncontradoException si la categoría asociada no existe.
     */
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
        );
    }

    /**
     * Actualiza un producto existente.
     *
     * @param id  Identificador del producto a actualizar.
     * @param dto DTO con los datos actualizados.
     * @return {@link ResponseDTO} con el producto actualizado.
     */
    @Override
    public ResponseDTO<ProductoResponseDTO> actualizar(Long id, ProductoUpdateDTO dto) {
        return null;
    }

    /**
     * Hace un borrado lógico de un producto de la base de datos.
     *
     * @param id Identificador del producto a eliminar.
     */
    @Override
    public void eliminar(Long id) {

    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id Identificador único del producto.
     * @return {@link ResponseDTO} con la información del producto encontrada.
     * @throws RecursoNoEncontradoException si el producto no existe.
     */
    @Override
    public ResponseDTO<ProductoResponseDTO> obtenerPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + id));

        return new ResponseDTO<ProductoResponseDTO>(
                productoMapper.toResponseDTO(producto),
                new ResponseDTO.EstadoDTO("Producto encontrado exitosamente", "200")
        );
    }

    /**
     * Obtiene una lista paginada de todos los productos.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de productos.
     */
    @Override
    public ResponseDTO<PaginaDTO<ProductoResponseDTO>> obtenerTodos(Pageable paginacion) {
        Page<ProductoResponseDTO> productoPage = productoRepository.findAll(paginacion)
                .map(item -> productoMapper.toResponseDTO(item));

        return new ResponseDTO<PaginaDTO<ProductoResponseDTO>>(
                new PaginaDTO<>(productoPage),
                new ResponseDTO.EstadoDTO("Lista de productos obtenida exitosamente", "200")
        );
    }

    public ProductoInitialResponseDTO getInicialCoordinador(Pageable pageable){

        Page<ProductoInitialDTO> productoPage=productoRepository.findAll(pageable).map(producto->this.toInitialDTO(producto));

        PaginaDTO<ProductoInitialDTO> productoDTO=new PaginaDTO<>(productoPage);

        int totales=productoDTO.paginacion().totalDeElementos().intValue();

        GlobalDTO global= GlobalDTO.builder()
                .totales(totales)
                .build();

        return new ProductoInitialResponseDTO(global,productoDTO);
    }

    public PaginaDTO<ProductoInitialDTO> getProductosCoordinador(Pageable pageable){

        Page<ProductoInitialDTO> productoPage=productoRepository.findAll(pageable).map(producto->this.toInitialDTO(producto));

        return new PaginaDTO<>(productoPage);
    }

    private ProductoInitialDTO toInitialDTO(Producto producto){

        return new ProductoInitialDTO(
                producto.getId(),
                producto.getNombre(),
                producto.getOrden(),
                producto.getCategoria().getNombre()
        );

    }



}



