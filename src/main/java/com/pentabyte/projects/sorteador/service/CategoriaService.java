package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.CategoriaUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaCreateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.exception.YaExisteElRecursoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.CategoriaMapper;
import com.pentabyte.projects.sorteador.model.Categoria;
import com.pentabyte.projects.sorteador.model.CategoriaTope;
import com.pentabyte.projects.sorteador.model.Grupo;
import com.pentabyte.projects.sorteador.model.Producto;
import com.pentabyte.projects.sorteador.repository.CategoriaRepository;
import com.pentabyte.projects.sorteador.repository.CategoriaTopeRepository;
import com.pentabyte.projects.sorteador.repository.GrupoRepository;
import com.pentabyte.projects.sorteador.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Servicio que gestiona la lógica de negocio de las categorías.
 * Proporciona operaciones CRUD para la entidad {@link Categoria}.
 */
@Service
public class CategoriaService implements CrudServiceInterface<CategoriaResponseDTO, Long, CategoriaCreateDTO, CategoriaUpdateDTO> {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;
    private final CategoriaTopeRepository categoriaTopeRepository;
    private final GrupoRepository grupoRepository;
    private final ProductoRepository productoRepository;
    @Autowired
    public CategoriaService(CategoriaMapper categoriaMapper, CategoriaRepository categoriaRepository, CategoriaTopeRepository categoriaTopeRepository, GrupoRepository grupoRepository, ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
        this.categoriaTopeRepository = categoriaTopeRepository;
        this.grupoRepository = grupoRepository;
        this.productoRepository = productoRepository;
    }

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param dto DTO con los datos necesarios para crear la categoría.
     * @return {@link ResponseDTO} con la información de la categoría creada.
     * @throws RecursoNoEncontradoException si la categoría asociada no existe.
     */
    @Transactional
    @Override
    public ResponseDTO<CategoriaResponseDTO> crear(CategoriaCreateDTO dto) {

        // Verificar si el nombre ya existe
        if (categoriaRepository.buscarNombre(dto.nombre()) > 0) {
            throw new YaExisteElRecursoException("Ya existe una categoría con el nombre: " + dto.nombre());
        }

        // Crear la categoría sin los grupos ni productos aún
        Categoria categoriaDb = this.categoriaRepository.save(
                new Categoria(
                        null,
                        dto.nombre(),
                        dto.ultimaSemanaDeAsignacion(),
                        dto.ultimaFechaDeAsignacion(),
                        dto.semanasAPlanificar(),
                        new ArrayList<>(),  // Lista de topes
                        new ArrayList<>(),  // Lista de grupos
                        new ArrayList<>()   // Lista de productos
                )
        );

        // Guardar los topes asociados a la categoría
        for (CategoriaTopeCreateDTO categoriaTope : dto.categoriaTopeList()) {
            this.categoriaTopeRepository.save(
                    new CategoriaTope(
                            null,
                            categoriaDb,
                            categoriaTope.cantidadMinima(),
                            categoriaTope.cantidadMaxima(),
                            categoriaTope.esAutoridad()
                    )
            );
        }

        // Guardar los grupos asociados a la categoría
        for (Long grupoId : dto.grupoList()) {
            Grupo grupoDb = this.grupoRepository.findById(grupoId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe el grupo con ID: " + grupoId));

            // Asociar la categoría al grupo
            grupoDb.setCategoria(categoriaDb);
            grupoRepository.save(grupoDb);

            // Agregar el grupo a la categoría
            categoriaDb.getGrupoList().add(grupoDb);
        }
        for (Long productoId : dto.productoList()) {
            Producto productoDb = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe el producto con ID: " + productoId));

            // Asociar la categoría al producto
            productoDb.setCategoria(categoriaDb);
            productoRepository.save(productoDb);

            // Agregar el producto a la categoría
            categoriaDb.getProductoList().add(productoDb);
        }

        // Guardar la categoría nuevamente con los grupos y productos agregados
        categoriaRepository.save(categoriaDb);

        // Mapear la categoría a la respuesta DTO
        CategoriaResponseDTO categoriaResponseDTO = categoriaMapper.toResponseDTO(categoriaDb);

        return new ResponseDTO<>(
                categoriaResponseDTO,
                new ResponseDTO.EstadoDTO(
                        "Categoría creada exitosamente",
                        "201")
                );
    }
    /**
     * Actualiza una categoría existente.
     *
     * @param id  Identificador de la categoría a actualizar.
     * @param dto DTO con los datos actualizados.
     * @return {@link ResponseDTO} con la categoría actualizada.
     */
    @Override
    public ResponseDTO<CategoriaResponseDTO> actualizar(Long id, CategoriaUpdateDTO dto) {
        return null;
    }

    /**
     * Hace un borrado lógico de una categoría de la base de datos.
     *
     * @param id Identificador de la categoría a eliminar.
     * @return {@link ResponseDTO} indicando el estado de la operación.
     */
    @Override
    public ResponseDTO<CategoriaResponseDTO> eliminar(Long id) {
        return null;
    }


    /**
     * Obtiene una categoría por su ID.
     *
     * @param id Identificador único de la categoría.
     * @return {@link ResponseDTO} con la información de la categoría encontrada.
     * @throws RecursoNoEncontradoException si la categoría  no existe.
     */
    @Override
    public ResponseDTO<CategoriaResponseDTO> obtenerPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + id));

        return new ResponseDTO<CategoriaResponseDTO>(
                categoriaMapper.toResponseDTO(categoria),
                new ResponseDTO.EstadoDTO("Categoría encontrada exitosamente", "200")
        );
    }

    /**
     * Obtiene una lista paginada de todas las categorías.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de categorías .
     */
    @Override
    public ResponseDTO<PaginaDTO<CategoriaResponseDTO>> obtenerTodos(Pageable paginacion) {

        Page<CategoriaResponseDTO> categoriaPage = categoriaRepository.findAll(paginacion)
                .map(item -> categoriaMapper.toResponseDTO(item));

        return new ResponseDTO<PaginaDTO<CategoriaResponseDTO>>(
                new PaginaDTO<CategoriaResponseDTO>(categoriaPage),
                new ResponseDTO.EstadoDTO("Lista de categorías obtenida exitosamente", "200")
        );
    }
}
