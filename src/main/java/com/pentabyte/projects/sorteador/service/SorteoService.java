package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.SorteoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.SorteoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.SorteoResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GlobalDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ProductoInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.SorteoInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.SorteoInitialResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.SorteoMapper;
import com.pentabyte.projects.sorteador.model.Producto;
import com.pentabyte.projects.sorteador.model.Sorteo;
import com.pentabyte.projects.sorteador.repository.ProductoRepository;
import com.pentabyte.projects.sorteador.repository.SorteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Servicio que gestiona la lógica de negocio de los sorteos.
 * Proporciona operaciones CRUD para la entidad {@link Sorteo}.
 */
@Service
public class SorteoService implements CrudServiceInterface<SorteoResponseDTO, Long, SorteoCreateDTO, SorteoUpdateDTO> {

    private final SorteoRepository sorteoRepository;
    private final ProductoRepository productoRepository;
    private final SorteoMapper sorteoMapper;

    @Autowired
    public SorteoService(SorteoRepository sorteoRepository, ProductoRepository productoRepository, SorteoMapper sorteoMapper) {
        this.sorteoRepository = sorteoRepository;
        this.productoRepository = productoRepository;
        this.sorteoMapper = sorteoMapper;
    }

    /**
     * Crea un nuevo sorteo en la base de datos.
     *
     * @param dto DTO con los datos necesarios para crear el sorteo.
     * @return {@link ResponseDTO} con la información del sorteo creado.
     * @throws RecursoNoEncontradoException si el producto asociado no existe.
     */
    @Override
    public ResponseDTO<SorteoResponseDTO> crear(SorteoCreateDTO dto) {

        Producto productoDb = productoRepository.findById(dto.productoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado con ID: " + dto.productoId()));

        Sorteo sorteo = sorteoRepository.save(new Sorteo(
                null,
                dto.fecha(),
                dto.confirmado(),
                dto.diaDescriptivo(),
                productoDb,
                new ArrayList<>()
        ));

        SorteoResponseDTO sorteoResponseDTO = sorteoMapper.toResponseDTO(sorteo);

        return new ResponseDTO<>(
                sorteoResponseDTO,
                new ResponseDTO.EstadoDTO(
                        "Sorteo creado exitosamente",
                        "201")
        );
    }

    /**
     * Actualiza un sorteo existente.
     *
     * @param id  Identificador del sorteo a actualizar.
     * @param dto DTO con los datos actualizados.
     * @return {@link ResponseDTO} con el sorteo actualizado.
     */
    @Override
    public ResponseDTO<SorteoResponseDTO> actualizar(Long id, SorteoUpdateDTO dto) {
        return null;
    }

    /**
     * Hace un borrado lógico de un sorteo de la base de datos.
     *
     * @param id Identificador del sorteo a eliminar.
     */
    @Override
    public void eliminar(Long id) {

    }

    /**
     * Obtiene un sorteo por su ID.
     *
     * @param id Identificador único del sorteo.
     * @return {@link ResponseDTO} con la información del sorteo encontrado.
     * @throws RecursoNoEncontradoException si el sorteo no existe.
     */
    @Override
    public ResponseDTO<SorteoResponseDTO> obtenerPorId(Long id) {
        Sorteo sorteo = sorteoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Sorteo no encontrado con ID: " + id));

        return new ResponseDTO<>(
                sorteoMapper.toResponseDTO(sorteo),
                new ResponseDTO.EstadoDTO("Sorteo encontrado exitosamente", "200")
        );
    }

    /**
     * Obtiene una lista paginada de todos los sorteos.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de sorteos.
     */
    @Override
    public ResponseDTO<PaginaDTO<SorteoResponseDTO>> obtenerTodos(Pageable paginacion) {

        Page<SorteoResponseDTO> sorteosPage = sorteoRepository.findAll(paginacion)
                .map(sorteoMapper::toResponseDTO);

        return new ResponseDTO<>(
                new PaginaDTO<>(sorteosPage),
                new ResponseDTO.EstadoDTO("Lista de sorteos obtenida exitosamente", "200")
        );
    }


    public SorteoInitialResponseDTO getInicialSorteosCoordinador(Pageable pageable, Long categoriaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {

        PaginaDTO<SorteoInitialDTO> paginaDTO = getSorteosCoordinador(pageable, categoriaId, fechaInicio, fechaFin);

        int confirmados = paginaDTO.contenido().stream().filter(SorteoInitialDTO::confirmado).toList().size();
        int noConfirmados = paginaDTO.contenido().stream().filter(sorteo -> !sorteo.confirmado()).toList().size();
        int totales = confirmados + noConfirmados;

        GlobalDTO globalDTO = GlobalDTO.builder()
                .totales(totales)
                .noConfirmados(noConfirmados)
                .confirmados(confirmados)
                .build();

        return new SorteoInitialResponseDTO(
                globalDTO,
                paginaDTO.contenido(),
                paginaDTO.paginacion()
        );
    }

    public PaginaDTO<SorteoInitialDTO> getSorteosCoordinador(Pageable pageable, Long categoriaId, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        Page<SorteoInitialDTO> sorteosPage = sorteoRepository.findByCategoriaAndFecha(pageable, categoriaId, fechaInicio, fechaFin).map(this::toInitialDTO);
        return new PaginaDTO<>(sorteosPage);
    }

    private SorteoInitialDTO toInitialDTO(Sorteo sorteo) {
        return new SorteoInitialDTO(
                sorteo.getId(),
                new ProductoInitialDTO(
                        sorteo.getProducto().getId(),
                        sorteo.getProducto().getNombre(),
                        sorteo.getProducto().getOrden(),
                        sorteo.getProducto().getCategoria().getNombre()
                ),
                sorteo.getFecha(),
                sorteo.getConfirmado()
        );
    }
}