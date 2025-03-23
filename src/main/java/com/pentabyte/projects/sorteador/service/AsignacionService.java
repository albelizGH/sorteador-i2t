package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.AsignacionUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.AsignacionCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.AsignacionResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.AsignacionMapper;
import com.pentabyte.projects.sorteador.mapper.SorteoMapper;
import com.pentabyte.projects.sorteador.model.Asignacion;
import com.pentabyte.projects.sorteador.model.Categoria;
import com.pentabyte.projects.sorteador.model.Grupo;
import com.pentabyte.projects.sorteador.model.Sorteo;
import com.pentabyte.projects.sorteador.repository.AsignacionRepository;
import com.pentabyte.projects.sorteador.repository.GrupoRepository;
import com.pentabyte.projects.sorteador.repository.SorteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona la lógica de negocio de las asignaciones.
 * Proporciona operaciones CRUD para la entidad {@link Asignacion}.
 */
@Service
public class AsignacionService implements CrudServiceInterface<AsignacionResponseDTO, Long, AsignacionCreateDTO, AsignacionUpdateDTO> {

    private final AsignacionRepository asignacionRepository;
    private final GrupoRepository grupoRepository;
    private final SorteoRepository sorteoRepository;
    private final AsignacionMapper asignacionMapper;
    private final SorteoMapper sorteoMapper;

    @Autowired
    public AsignacionService(AsignacionRepository asignacionRepository, GrupoRepository grupoRepository, SorteoRepository sorteoRepository, AsignacionMapper asignacionMapper, SorteoMapper sorteoMapper) {
        this.asignacionRepository = asignacionRepository;
        this.grupoRepository = grupoRepository;
        this.sorteoRepository = sorteoRepository;
        this.asignacionMapper = asignacionMapper;
        this.sorteoMapper = sorteoMapper;
    }

    @Override
    public ResponseDTO<AsignacionResponseDTO> crear(AsignacionCreateDTO dto) {

        Grupo grupo = grupoRepository.findById(dto.grupoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Grupo no encontrada con ID: " + dto.grupoId()));

        Sorteo sorteo = sorteoRepository.findById(dto.grupoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Sorteo no encontrada con ID: " + dto.grupoId()));

        Asignacion asignacionDb = asignacionRepository.save(new Asignacion(
                null,
                dto.estado(),
                grupo,
                sorteo,
                new ArrayList<>(),
                new ArrayList<>()
        ));

        return new ResponseDTO<>(
                asignacionMapper.toResponseDTO(asignacionDb),
                new ResponseDTO.EstadoDTO("Asignación creada exitosamente", "201")
        );
    }

    @Override
    public ResponseDTO<AsignacionResponseDTO> actualizar(Long id, AsignacionUpdateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<AsignacionResponseDTO> eliminar(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<AsignacionResponseDTO> obtenerPorId(Long id) {
        Asignacion asignacion = asignacionRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Asignación no encontrada con ID: " + id));

        return new ResponseDTO<>(
                asignacionMapper.toResponseDTO(asignacion),
                new ResponseDTO.EstadoDTO("Asignación encontrada exitosamente", "200")
        );
    }

    @Override
    public ResponseDTO<PaginaDTO<AsignacionResponseDTO>> obtenerTodos(Pageable paginacion) {
        Page<AsignacionResponseDTO> asignacionPage = asignacionRepository.findAll(paginacion)
                .map(asignacionMapper::toResponseDTO);

        return new ResponseDTO<>(
                new PaginaDTO<>(asignacionPage),
                new ResponseDTO.EstadoDTO("Lista de asignaciones obtenida exitosamente", "200")
        );
    }

    public List<?> ejecutarPlanificacion(Integer semanasAPlanificar) {
        // >Recibo las semanas a planificar//
        LocalDateTime fechaActual = LocalDateTime.now();
        LocalDateTime fechaFin = LocalDateTime.now().plusWeeks(semanasAPlanificar);
        List<Sorteo> sorteoList = this.asignacionRepository.findSorteosEntreFechas(fechaActual, fechaFin);
        sorteoList.stream().map(sorteoMapper::toResponseDTO).toList();

        //Buscamos los sorteos que coincidan desde la fecha actual hasta el total de las semanas a planificadas recibidas//
        List<Categoria> categorias = sorteoList.stream()
                .map(sorteo -> sorteo.getProducto().getCategoria())
                .collect(Collectors.toList());

        categorias.stream().map(categoria -> categoria.getId()).collect(Collectors.toList());

        // >Desde la tabla de asignación por cada categoría buscamos  según la ultima fecha de asignación el grupo que fue asignado//
        return null;
    }
}

