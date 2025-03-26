package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.CategoriaTopeUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.CategoriaTopeInitialDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.CategoriaTopeMapper;
import com.pentabyte.projects.sorteador.model.CategoriaTope;
import com.pentabyte.projects.sorteador.repository.CategoriaRepository;
import com.pentabyte.projects.sorteador.repository.CategoriaTopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Servicio que gestiona la lógica de negocio de las categorías tope.
 * Proporciona operaciones CRUD para la entidad {@link CategoriaTope}.
 */
@Service
public class CategoriaTopeService implements CrudServiceInterface<CategoriaTopeResponseDTO, Long, CategoriaTopeCreateDTO, CategoriaTopeUpdateDTO> {

    private final CategoriaTopeRepository categoriaTopeRepository;
    private final CategoriaRepository categoriaRepository;
    private final CategoriaTopeMapper categoriaTopeMapper;

    @Autowired
    public CategoriaTopeService(CategoriaTopeRepository categoriaTopeRepository, CategoriaRepository categoriaRepository, CategoriaTopeMapper categoriaTopeMapper) {
        this.categoriaTopeRepository = categoriaTopeRepository;
        this.categoriaRepository = categoriaRepository;
        this.categoriaTopeMapper = categoriaTopeMapper;
    }

    /**
     * Crea una nueva categoría tope en la base de datos.
     *
     * @param dto DTO con los datos necesarios para crear la categoría tope.
     * @return {@link ResponseDTO} con la información de la categoría tope creada.
     * @throws RecursoNoEncontradoException si la categoría asociada no existe.
     */
    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> crear(CategoriaTopeCreateDTO dto) {

        CategoriaTope categoriaTope = new CategoriaTope(
                null,
                null,
                dto.cantidadMinima(),
                dto.cantidadMaxima(),
                dto.esAutoridad()
        );

        CategoriaTope categoriaTopeDb = categoriaTopeRepository.save(categoriaTope);

        CategoriaTopeResponseDTO categoriaTopeResponseDTO = categoriaTopeMapper.toResponseDTO(categoriaTopeDb);

        return new ResponseDTO<CategoriaTopeResponseDTO>(
                categoriaTopeResponseDTO,
                new ResponseDTO.EstadoDTO(
                        "Categoría tope creada exitosamente",
                        "201")
        );
    }

    /**
     * Actualiza una categoría tope existente.
     *
     * @param id                     Identificador de la categoría tope a actualizar.
     * @param categoriaTopeUpdateDTO DTO con los datos actualizados.
     * @return {@link ResponseDTO} con la categoría tope actualizada.
     */
    @Transactional
    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> actualizar(Long id, CategoriaTopeUpdateDTO categoriaTopeUpdateDTO) {

        CategoriaTope categoriaTope = categoriaTopeRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría tope no encontrada con ID: " + id));

        Integer cantidadMaxima = this.categoriaTopeRepository.obtenerCantidadMaximaPorCategoriaTope(id);

        Integer cantidadIntegrantes = 0;

        if (categoriaTopeUpdateDTO.cantidadMaxima() < cantidadMaxima) {

            if (categoriaTope.getEsAutoridad()) {
                cantidadIntegrantes = this.categoriaTopeRepository.obtenerCantidadIntegrantes(categoriaTope.getCategoria().getId(), "Autoridad");
            } else {
                cantidadIntegrantes = this.categoriaTopeRepository.obtenerCantidadIntegrantes(categoriaTope.getCategoria().getId(), "Auxiliar");
            }

            if (cantidadIntegrantes > categoriaTopeUpdateDTO.cantidadMaxima()) {
                throw new IllegalArgumentException("La cantidad de integrantes supera el límite ingresado para esta categoría. " +
                        "La cantidad actual de integrantes es " + cantidadIntegrantes + " y el máximo permitido que acaba de ingresar es " + categoriaTopeUpdateDTO.cantidadMaxima() + "." +
                        "Modificalo e intentalo con ingresar nuevamente");
            }

        } else {
            categoriaTope.setCantidadMaxima(categoriaTopeUpdateDTO.cantidadMaxima());
            categoriaTopeRepository.save(categoriaTope);
        }

        return new ResponseDTO<CategoriaTopeResponseDTO>(
                categoriaTopeMapper.toResponseDTO(categoriaTope),
                new ResponseDTO.EstadoDTO("Categoría tope encontrada exitosamente", "200")
        );

    }

    /**
     * Hace un borrado lógico de una categoría tope de la base de datos.
     *
     * @param id Identificador de la categoría tope a eliminar.
     * @return {@link ResponseDTO} indicando el estado de la operación.
     */
    @Override
    public void eliminar(Long id) {

    }

    /**
     * Obtiene una categoría tope por su ID.
     *
     * @param id Identificador único de la categoría tope.
     * @return {@link ResponseDTO} con la información de la categoría tope encontrada.
     * @throws RecursoNoEncontradoException si la categoría tope no existe.
     */
    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> obtenerPorId(Long id) {
        CategoriaTope categoriaTope = categoriaTopeRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría tope no encontrada con ID: " + id));

        return new ResponseDTO<CategoriaTopeResponseDTO>(
                categoriaTopeMapper.toResponseDTO(categoriaTope),
                new ResponseDTO.EstadoDTO("Categoría tope encontrada exitosamente", "200")
        );
    }

    /**
     * Obtiene una lista paginada de todas las categorías tope.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de categorías tope.
     */
    @Override
    public ResponseDTO<PaginaDTO<CategoriaTopeResponseDTO>> obtenerTodos(Pageable paginacion) {

        Page<CategoriaTopeResponseDTO> categoriaTopesPage = categoriaTopeRepository.findAll(paginacion)
                .map(item -> categoriaTopeMapper.toResponseDTO(item));

        return new ResponseDTO<PaginaDTO<CategoriaTopeResponseDTO>>(
                new PaginaDTO<>(categoriaTopesPage),
                new ResponseDTO.EstadoDTO("Lista de categorías topes obtenida exitosamente", "200")
        );
    }


    /**
     * Obtiene una lista paginada de todas las categorías tope.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de categorías tope.
     */
    public ResponseDTO<PaginaDTO<CategoriaTopeInitialDTO>> obtenerCategoriasCoordinador(Pageable paginacion) {

        Page<CategoriaTopeInitialDTO> categoriaTopesPage = categoriaTopeRepository.findAll(paginacion)
                .map(item -> new CategoriaTopeInitialDTO(
                        item.getId(),
                        item.getCantidadMinima(),
                        item.getCantidadMaxima(),
                        item.getEsAutoridad()
                ));

        return new ResponseDTO<PaginaDTO<CategoriaTopeInitialDTO>>(
                new PaginaDTO<CategoriaTopeInitialDTO>(categoriaTopesPage.getContent(), new PaginaDTO.PaginacionDTO(categoriaTopesPage.getNumberOfElements(), categoriaTopesPage.getTotalElements())),
                new ResponseDTO.EstadoDTO("Categorías tope recuperadas correctamente", "200")
        );
    }
}
