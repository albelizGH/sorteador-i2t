package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.CategoriaUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.CategoriaInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.CategoriaInitialResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.CategoriaTopeInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GlobalDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.exception.YaExisteElRecursoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.CategoriaMapper;
import com.pentabyte.projects.sorteador.model.Categoria;
import com.pentabyte.projects.sorteador.model.CategoriaTope;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                        null,
                        null,
                        null,
                        new ArrayList<>(),  // Lista de topes
                        new ArrayList<>(),  // Lista de grupos
                        new ArrayList<>()   // Lista de productos
                )
        );

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
    public void eliminar(Long id) {

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


    public CategoriaInitialResponseDTO getInicialCoordinador(Pageable paginacion) {
        Page<CategoriaInitialDTO> categoriaPage = categoriaRepository.findAll(paginacion).map(this::categoriaInitialMapper);
        Page<CategoriaTopeInitialDTO> categoriaTopePage = categoriaTopeRepository.findAll(paginacion).map(this::toInitialDTO);

        PaginaDTO<CategoriaInitialDTO> categoriaDTO = new PaginaDTO<>(categoriaPage);
        PaginaDTO<CategoriaTopeInitialDTO> categoriaTopeDTO = new PaginaDTO<>(categoriaTopePage);


        int totales = categoriaDTO.paginacion().totalDeElementos().intValue();

        GlobalDTO global = GlobalDTO.builder()
                .totales(totales)
                .build();

        return new CategoriaInitialResponseDTO(global, categoriaDTO.contenido(), categoriaTopeDTO.contenido(), categoriaDTO.paginacion(), categoriaTopeDTO.paginacion());

    }

    public PaginaDTO<CategoriaInitialDTO> getCategoriasCoordinador(Pageable paginacion) {
        Page<CategoriaInitialDTO> categoriaPage = categoriaRepository.findAll(paginacion).map(categoria -> this.categoriaInitialMapper(categoria));
        return new PaginaDTO<>(categoriaPage);
    }

    private CategoriaInitialDTO categoriaInitialMapper(Categoria categoria) {

        Integer cantidadMaximaDeAutoridades = categoria.getCategoriaTopeList().stream().filter(CategoriaTope::getEsAutoridad).map(CategoriaTope::getCantidadMaxima).reduce(0, Integer::sum);
        Integer cantidadMaximaDeAuxiliares = categoria.getCategoriaTopeList().stream().filter(categoriaTope -> !categoriaTope.getEsAutoridad()).map(CategoriaTope::getCantidadMaxima).reduce(0, Integer::sum);
        Integer cantidadMinimaDeAuxiliares = categoria.getCategoriaTopeList().stream().filter(categoriaTope -> !categoriaTope.getEsAutoridad()).map(CategoriaTope::getCantidadMinima).reduce(0, Integer::sum);
        Integer cantidadMinimaDeAutoridades = categoria.getCategoriaTopeList().stream().filter(CategoriaTope::getEsAutoridad).map(CategoriaTope::getCantidadMinima).reduce(0, Integer::sum);

        return new CategoriaInitialDTO(
                categoria.getId(),
                categoria.getNombre(),
                cantidadMaximaDeAutoridades,
                cantidadMinimaDeAutoridades,
                cantidadMaximaDeAuxiliares,
                cantidadMinimaDeAuxiliares
        );

    }

    private CategoriaTopeInitialDTO toInitialDTO(CategoriaTope categoria) {
        return new CategoriaTopeInitialDTO(
                categoria.getId(),
                categoria.getCantidadMinima(),
                categoria.getCantidadMaxima(),
                categoria.getEsAutoridad()
        );
    }

    public CategoriaInitialDTO crearCategoria(CategoriaCreateDTO dto) {

        // Verificar si el nombre ya existe
        if (categoriaRepository.buscarNombre(dto.nombre()) > 0) {
            throw new YaExisteElRecursoException("Ya existe una categoría con el nombre: " + dto.nombre());
        }

        List<CategoriaTope> lista = dto.categoriaTopeIdList().stream()
                .map(item -> categoriaTopeRepository.getReferenceById(item))
                .collect(Collectors.toList());


        // Crear la categoría sin los grupos ni productos aún
        Categoria categoriaDb = this.categoriaRepository.save(
                new Categoria(
                        null,
                        dto.nombre(),
                        0,
                        null,
                        0,
                        lista,  // Lista de topes
                        new ArrayList<>(),  // Lista de grupos
                        new ArrayList<>()   // Lista de productos
                )
        );

        // Guardar la categoría nuevamente con los grupos y productos agregados
        categoriaRepository.save(categoriaDb);
        Optional<CategoriaTope> categoriaTopeAutoridad = categoriaDb.getCategoriaTopeList()
                .stream().filter(CategoriaTope::getEsAutoridad)
                .findFirst();

        Optional<CategoriaTope> categoriaTopeAuxiliar = categoriaDb.getCategoriaTopeList()
                .stream().filter(categoriaTope -> categoriaTope.getEsAutoridad() == Boolean.FALSE)
                .findFirst();

        // Mapear la categoría a la respuesta DTO
        return new CategoriaInitialDTO(
                categoriaDb.getId(),
                categoriaDb.getNombre(),
                categoriaTopeAutoridad.get().getCantidadMaxima(),
                categoriaTopeAutoridad.get().getCantidadMinima(),
                categoriaTopeAuxiliar.get().getCantidadMaxima(),
                categoriaTopeAuxiliar.get().getCantidadMinima()
        );
    }
}
