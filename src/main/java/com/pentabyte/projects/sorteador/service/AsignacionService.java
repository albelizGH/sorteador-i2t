package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.consultas.planificacion.GrupoPlanificacionDTO;
import com.pentabyte.projects.sorteador.dto.consultas.planificacion.IdSorteoCategoriaDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.AsignacionUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.AsignacionCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.AsignacionResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.AsignacionMapper;
import com.pentabyte.projects.sorteador.mapper.SorteoMapper;
import com.pentabyte.projects.sorteador.model.Asignacion;
import com.pentabyte.projects.sorteador.model.Estado;
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
import java.util.*;
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

    private Map<Long, Long> getUltimosGruposPorCategoriaPlanificados(List<Long> idsCategorias) {
        List<Object[]> resultados = asignacionRepository.findUltimosGruposPorCategoriaPlanificados(idsCategorias);
        Map<Long, Long> mapa = new HashMap<>();

        for (Object[] fila : resultados) {
            Long grupoId = ((Number) fila[0]).longValue();
            Long categoriaId = ((Number) fila[1]).longValue();
            mapa.put(categoriaId, grupoId);
        }

        return mapa;
    }

    public Map<Long, Integer> getGruposOrdenPorCategorias(List<Long> idsCategorias) {
        List<Object[]> resultados = grupoRepository.findGruposOrdenPorCategorias(idsCategorias);
        Map<Long, Integer> mapa = new HashMap<>();

        for (Object[] fila : resultados) {
            Long grupoId = ((Number) fila[0]).longValue();
            Integer ordenDeGrupo = ((Number) fila[1]).intValue();
            mapa.put(grupoId, ordenDeGrupo);
        }

        return mapa;
    }

    public Map<Long, Integer> getCategoriaCantidadGruposPorCategorias(List<Long> idsCategorias) {
        List<Object[]> resultados = grupoRepository.findCategoriaCantidadGruposPorCategorias(idsCategorias);
        Map<Long, Integer> mapa = new HashMap<>();

        for (Object[] fila : resultados) {
            Long categoriaId = ((Number) fila[0]).longValue();
            Integer cantidadGrupos = ((Number) fila[1]).intValue();
            mapa.put(categoriaId, cantidadGrupos);
        }

        return mapa;
    }


    public ResponseDTO<PaginaDTO<AsignacionResponseDTO>> planificar(int semanas) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fin = ahora.plusWeeks(semanas);

        //Obtengo todos los sorteos que se realizarán en el rango de fechas con sus respectivas categorias
        List<IdSorteoCategoriaDTO> sorteosYcategorias = sorteoRepository.findIdSorteosConfirmadosEntreFechas(ahora, fin);

        if (sorteosYcategorias.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay sorteos confirmados en el rango de fechas");
        }

        List<Long> idsCategorias = sorteosYcategorias.stream().map(IdSorteoCategoriaDTO::categoriaId).collect(Collectors.toList());

        if (idsCategorias.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay categorias en los sorteos confirmados en el rango de fechas");
        }

        List<GrupoPlanificacionDTO> grupos = grupoRepository.findGruposPoridsCategorias(idsCategorias);

        //Lista de grupos ordenada por orden de grupo
        List<GrupoPlanificacionDTO> gruposOrdenadosPorOrden = grupos.stream().sorted(Comparator.comparing(GrupoPlanificacionDTO::orden)).collect(Collectors.toList());

        //Mapa de grupos ordenados por orden de grupo por categoria
        Map<Long, List<GrupoPlanificacionDTO>> gruposPorCategoria = grupos.stream().collect(Collectors.groupingBy(grupo -> grupo.idCategoria()));

        //Mapa de cantidad de grupos por categoria
        Map<Long, Integer> cantidadDeGruposPorCategoria = gruposPorCategoria.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));

        //Mapa de ultimos grupos planificados por categoria
        Map<Long, Long> ultimosGruposPorCategoriaPlanificados = getUltimosGruposPorCategoriaPlanificados(idsCategorias);

        List<Asignacion> asignaciones = new ArrayList<>();

        //Busco si para este rango de fechas ya hay asignaciones borrador y si las hay, las elimino para volver a planificar
        List<Long> asignacionesBorradorids = asignacionRepository.findAsignacionesBorradorEntreFechas(ahora, fin);
        if (!asignacionesBorradorids.isEmpty()) {
            asignacionRepository.deleteAllById(asignacionesBorradorids);
        }


        for (IdSorteoCategoriaDTO sorteo : sorteosYcategorias) {
            Long sorteoId = sorteo.sorteoId();
            Long categoriaId = sorteo.categoriaId();
            Integer cantidadDeGruposEnEstaCategoria = cantidadDeGruposPorCategoria.get(categoriaId);
            List<GrupoPlanificacionDTO> gruposDeLaCategoria = gruposPorCategoria.get(categoriaId);

            Long ultimoGrupoAsignadoId = ultimosGruposPorCategoriaPlanificados.get(categoriaId);

            if (ultimoGrupoAsignadoId == null) {
                ultimoGrupoAsignadoId = gruposDeLaCategoria.get(0).id();
            }

            Long finalUltimoGrupoAsignadoId = ultimoGrupoAsignadoId;

            //Categoria 1-> grupo 4 orden 1, grupo 5 orden 2, grupo 6 orden 3

            Integer ordenDeUltimoGrupoAsignado = gruposDeLaCategoria.stream()
                    .filter(grupo -> grupo.id().equals(finalUltimoGrupoAsignadoId))
                    .findFirst()
                    .map(GrupoPlanificacionDTO::orden)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el grupo con ID: " + finalUltimoGrupoAsignadoId));

            int indiceUltimoGrupoAsignado = gruposDeLaCategoria.indexOf(gruposDeLaCategoria.stream()
                    .filter(grupo -> grupo.id().equals(finalUltimoGrupoAsignadoId))
                    .findFirst()
                    .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el grupo con ID: " + finalUltimoGrupoAsignadoId)));

            Long siguienteGrupoId;

            if (indiceUltimoGrupoAsignado + 1 == cantidadDeGruposEnEstaCategoria) {
                siguienteGrupoId = gruposPorCategoria.get(categoriaId).get(0).id();
            } else {
                siguienteGrupoId = gruposPorCategoria.get(categoriaId).get(indiceUltimoGrupoAsignado + 1).id();
            }

            int ordenSiguienteGrupo = gruposPorCategoria.get(categoriaId).stream()
                    .filter(grupo -> grupo.id().equals(siguienteGrupoId))
                    .findFirst()
                    .map(GrupoPlanificacionDTO::orden)
                    .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el grupo con ID: " + siguienteGrupoId));

            System.out.println("");
            System.out.println("Sorteo id: " + sorteoId);
            System.out.println("Categoria id: " + categoriaId);
            System.out.println("Cantidad de grupos en esta categoria: " + cantidadDeGruposEnEstaCategoria);
            System.out.println("Grupos de la categoria: " + gruposDeLaCategoria);
            System.out.println("Ultimo grupo asignado id: " + finalUltimoGrupoAsignadoId);
            System.out.println("Indice del ultimo grupo asignado: " + indiceUltimoGrupoAsignado);
            System.out.println("Orden de ultimo grupo asignado: " + ordenDeUltimoGrupoAsignado);
            System.out.println("Siguiente grupo id: " + siguienteGrupoId);
            System.out.println("Orden del siguiente grupo: " + ordenSiguienteGrupo);


            Asignacion asignacion = new Asignacion(
                    Estado.BORRADOR,
                    grupoRepository.getReferenceById(siguienteGrupoId),
                    sorteoRepository.getReferenceById(sorteoId),
                    new ArrayList<>(),
                    new ArrayList<>()
            );

            //Ahora tengo que guardar en el ultimo grupo asignado el grupo que acabo de asignar
            ultimosGruposPorCategoriaPlanificados.put(categoriaId, siguienteGrupoId);
            System.out.println("ultimosGrupos modificado = " + ultimosGruposPorCategoriaPlanificados);

            asignaciones.add(asignacion);
        }

        List<Asignacion> asignacionesDB = asignacionRepository.saveAll(asignaciones);

        List<AsignacionResponseDTO> asignacionResponseDTOS = asignacionesDB.stream()
                .map(asignacionMapper::toResponseDTO)
                .collect(Collectors.toList());

        int elementosPorPagina = 5;
        int totalDePaginas = (int) Math.ceil((double) asignacionesDB.size() / elementosPorPagina);

        PaginaDTO<AsignacionResponseDTO> asignacionesDTO = new PaginaDTO<AsignacionResponseDTO>(
                asignacionResponseDTOS,
                new PaginaDTO.PaginacionDTO(
                        elementosPorPagina,
                        (long) asignacionesDB.size(),
                        totalDePaginas,
                        0,
                        totalDePaginas - 1 == 0
                )
        );

        return new ResponseDTO<>(
                asignacionesDTO,
                new ResponseDTO.EstadoDTO("Planificación ejecutada exitosamente", "200")
        );

        /*
         * La sobrescritura de fechas en la tabla categoria se realiza cuando el coordinador pasa la planificacion a estado confirmado
         * */


    }

}

