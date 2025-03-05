package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.CategoriaUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.CategoriaMapper;
import com.pentabyte.projects.sorteador.model.Categoria;
import com.pentabyte.projects.sorteador.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Servicio que gestiona la lógica de negocio de las categorías.
 * Proporciona operaciones CRUD para la entidad {@link Categoria}.
 */
@Service
public class CategoriaService implements CrudServiceInterface<CategoriaResponseDTO, Long, CategoriaCreateDTO, CategoriaUpdateDTO> {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Autowired
    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param dto DTO con los datos necesarios para crear la categoría.
     * @return {@link ResponseDTO} con la información de la categoría creada.
     * @throws RecursoNoEncontradoException si la categoría asociada no existe.
     */
    @Override
    public ResponseDTO<CategoriaResponseDTO> crear(CategoriaCreateDTO dto) {

        Categoria categoriaDb = categoriaRepository.save(new Categoria(
                null,
                dto.nombre(),
                dto.ultimaSemanaDeAsignacion(),
                dto.ultimaFechaDeAsignacion(),
                dto.semanasAPlanificar(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        ));

        CategoriaResponseDTO categoriaResponseDTO = categoriaMapper.toResponseDTO(categoriaDb);

        return new ResponseDTO<CategoriaResponseDTO>(
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
