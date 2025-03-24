package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.consultas.planificacion.GrupoPlanificacionDTO;
import com.pentabyte.projects.sorteador.dto.consultas.planificacion.IdSorteoCategoriaDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.AsignacionUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.AsignacionCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.AsignacionResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.AsignacionInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.AsignacionInitialResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GlobalDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.AsignacionMapper;
import com.pentabyte.projects.sorteador.mapper.SorteoMapper;
import com.pentabyte.projects.sorteador.model.*;
import com.pentabyte.projects.sorteador.repository.AsignacionRepository;
import com.pentabyte.projects.sorteador.repository.GrupoRepository;
import com.pentabyte.projects.sorteador.repository.SorteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    /**
     * Método que planifica los sorteos en un rango de semanas, asignando grupos a los sorteos
     * y generando un listado de asignaciones. Si ya existen asignaciones de borrador dentro
     * del rango de fechas, estas serán eliminadas antes de volver a planificar.
     *
     * @param semanas El número de semanas a planificar desde el momento actual.
     *                El método calcula las fechas de inicio y fin a partir de la fecha actual.
     * @return Un objeto {@link ResponseDTO} que contiene una {@link PaginaDTO} con el listado de asignaciones
     * planificadas y su paginación correspondiente.
     * @throws RecursoNoEncontradoException Si no se encuentran sorteos confirmados o categorías dentro del rango de fechas.
     */
    public ResponseDTO<PaginaDTO<AsignacionResponseDTO>> planificar(int semanas) {
        /*
         * Lógica de la planificación automática:
         *
         * Obtener las fechas y el rango de planificación.
         * Consultar los sorteos confirmados dentro del rango de fechas.
         * Obtener las categorías relacionadas con esos sorteos.
         * Obtener los grupos asociados a esas categorías y ordenarlos según el orden secuencial.
         * Calcular cuántos grupos hay por categoría y obtener los últimos grupos planificados por categoría.
         * Eliminar asignaciones en estado Borrador para el mismo rango de fechas para sobrescribirlas si vuelvo a ejecutar el método.
         * Iterar sobre cada sorteo-categoría, asignando grupos de acuerdo al orden.
         * Actualizar el estado de las asignaciones.
         * Calcular la paginación y devolver la respuesta formateada.
         * */

        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime fin = ahora.plusWeeks(semanas);

        // Obtengo todos los sorteos confirmados dentro del rango de fechas especificado
        List<IdSorteoCategoriaDTO> sorteosYcategorias = obtenerSorteosConfirmadosEnRango(ahora, fin);

        // Obtengo los IDs de las categorías de los sorteos
        List<Long> idsCategorias = obtenerIdsCategorias(sorteosYcategorias);

        // Busco los grupos asociados a las categorías
        List<GrupoPlanificacionDTO> grupos = grupoRepository.findGruposPoridsCategorias(idsCategorias);

        // Ordeno los grupos por su orden
        List<GrupoPlanificacionDTO> gruposOrdenadosPorOrden = ordenarGruposPorOrden(grupos);

        // Agrupo los grupos por categoría
        Map<Long, List<GrupoPlanificacionDTO>> gruposPorCategoria = agruparGruposPorCategoria(gruposOrdenadosPorOrden);

        // Calcula la cantidad de grupos por categoría
        Map<Long, Integer> cantidadDeGruposPorCategoria = obtenerCantidadDeGruposPorCategoria(gruposPorCategoria);

        // Obtengo los últimos grupos planificados por cada categoría
        Map<Long, Long> ultimosGruposPorCategoriaPlanificados = getUltimosGruposPorCategoriaPlanificados(idsCategorias);

        // Elimino las asignaciones de borrador dentro del rango de fechas para poder planificar de nuevo
        eliminarAsignacionesBorrador(ahora);

        // Lista para almacenar las asignaciones
        List<Asignacion> asignaciones = new ArrayList<>();

        // Itero sobre los sorteos y categorías para realizar las asignaciones
        for (IdSorteoCategoriaDTO sorteo : sorteosYcategorias) {
            Long sorteoId = sorteo.sorteoId();
            Long categoriaId = sorteo.categoriaId();
            Integer cantidadDeGruposEnEstaCategoria = cantidadDeGruposPorCategoria.get(categoriaId);
            List<GrupoPlanificacionDTO> gruposDeLaCategoria = gruposPorCategoria.get(categoriaId);
            Long ultimoGrupoAsignadoId = ultimosGruposPorCategoriaPlanificados.get(categoriaId);

            if (ultimoGrupoAsignadoId == null) {
                ultimoGrupoAsignadoId = gruposDeLaCategoria.get(0).id();
            }

            // Obtengo el índice del último grupo asignado
            Long finalUltimoGrupoAsignadoId = ultimoGrupoAsignadoId;

            int indiceUltimoGrupoAsignado = obtenerIndiceUltimoGrupoAsignado(gruposDeLaCategoria, finalUltimoGrupoAsignadoId);

            // Busco el siguiente grupo a asignar
            Long siguienteGrupoId = obtenerSiguienteGrupoId(gruposDeLaCategoria, categoriaId, indiceUltimoGrupoAsignado, cantidadDeGruposEnEstaCategoria);

            // Creo la asignación
            Asignacion asignacion = crearAsignacion(siguienteGrupoId, sorteoId, categoriaId);

            // Actualizo el último grupo asignado
            ultimosGruposPorCategoriaPlanificados.put(categoriaId, siguienteGrupoId);

            asignaciones.add(asignacion);
        }

        List<Asignacion> asignacionesDB = asignacionRepository.saveAll(asignaciones);

        // Mapear las asignaciones a DTOs
        List<AsignacionResponseDTO> asignacionResponseDTOS = asignacionesDB.stream()
                .map(asignacionMapper::toResponseDTO)
                .collect(Collectors.toList());

        // Crear la paginación de las asignaciones
        PaginaDTO<AsignacionResponseDTO> asignacionesDTO = crearPaginacion(asignacionResponseDTOS, 5);

        return new ResponseDTO<>(
                asignacionesDTO,
                new ResponseDTO.EstadoDTO("Planificación ejecutada exitosamente", "200")
        );

        /*
         * La sobrescritura de fechas en la tabla categoria se realiza cuando el coordinador pasa la planificacion a estado confirmado
         * */

    }








    /*METODOS AUXILIARES*/


    /**
     * Obtiene los sorteos confirmados dentro de un rango de fechas especificado.
     *
     * @param ahora Fecha y hora de inicio del rango.
     * @param fin   Fecha y hora de fin del rango.
     * @return Lista de sorteos confirmados dentro del rango de fechas.
     * @throws RecursoNoEncontradoException Si no se encuentran sorteos confirmados en el rango de fechas.
     */
    private List<IdSorteoCategoriaDTO> obtenerSorteosConfirmadosEnRango(LocalDateTime ahora, LocalDateTime fin) {
        List<IdSorteoCategoriaDTO> sorteosYcategorias = sorteoRepository.findIdSorteosConfirmadosEntreFechas(ahora, fin);
        if (sorteosYcategorias.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay sorteos confirmados en el rango de fechas");
        }
        return sorteosYcategorias;
    }

    /**
     * Extrae los IDs de las categorías de una lista de sorteos y categorías.
     *
     * @param sorteosYcategorias Lista de sorteos y categorías.
     * @return Lista de IDs de las categorías.
     * @throws RecursoNoEncontradoException Si no se encuentran categorías en los sorteos confirmados.
     */
    private List<Long> obtenerIdsCategorias(List<IdSorteoCategoriaDTO> sorteosYcategorias) {
        List<Long> idsCategorias = sorteosYcategorias.stream()
                .map(IdSorteoCategoriaDTO::categoriaId)
                .collect(Collectors.toList());

        if (idsCategorias.isEmpty()) {
            throw new RecursoNoEncontradoException("No hay categorias en los sorteos confirmados en el rango de fechas");
        }
        return idsCategorias;
    }

    /**
     * Elimina las asignaciones de tipo borrador que se encuentren dentro de un rango de fechas mayor al dado.
     *
     * @param ahora Fecha y hora de inicio del rango.
     */
    private void eliminarAsignacionesBorrador(LocalDateTime ahora) {
        List<Long> asignacionesBorradorids = asignacionRepository.findAsignacionesBorradorConFechaMayorA(ahora);
        if (!asignacionesBorradorids.isEmpty()) {
            asignacionRepository.deleteAllById(asignacionesBorradorids);
        }
    }

    /**
     * Obtiene el ID del siguiente grupo a asignar, basándose en el índice del último grupo asignado y la cantidad total de grupos en una categoría.
     *
     * @param gruposDeLaCategoria             Lista de grupos de una categoría, ordenados según el criterio de asignación.
     * @param categoriaId                     ID de la categoría a la que pertenece el grupo.
     * @param indiceUltimoGrupoAsignado       Índice del último grupo que se asignó.
     * @param cantidadDeGruposEnEstaCategoria Cantidad total de grupos en la categoría.
     * @return El ID del siguiente grupo a asignar.
     */
    private Long obtenerSiguienteGrupoId(List<GrupoPlanificacionDTO> gruposDeLaCategoria, Long categoriaId, int indiceUltimoGrupoAsignado, Integer cantidadDeGruposEnEstaCategoria) {
        if (indiceUltimoGrupoAsignado + 1 == cantidadDeGruposEnEstaCategoria) {
            return gruposDeLaCategoria.get(0).id();  // Vuelve al primer grupo
        } else {
            return gruposDeLaCategoria.get(indiceUltimoGrupoAsignado + 1).id();
        }
    }

    /**
     * Crea una nueva asignación para un grupo, sorteo y categoría específicos.
     *
     * @param siguienteGrupoId ID del siguiente grupo que se asignará.
     * @param sorteoId         ID del sorteo al que se asignará el grupo.
     * @param categoriaId      ID de la categoría asociada con el sorteo y grupo.
     * @return Una nueva instancia de asignación.
     */
    private Asignacion crearAsignacion(Long siguienteGrupoId, Long sorteoId, Long categoriaId) {
        return new Asignacion(
                Estado.BORRADOR,
                grupoRepository.getReferenceById(siguienteGrupoId),
                sorteoRepository.getReferenceById(sorteoId),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    /**
     * Calcula la cantidad de grupos por cada categoría.
     * <p>
     * Este método toma un mapa de grupos por categoría y calcula cuántos grupos hay
     * en cada categoría. Devuelve un mapa donde la clave es el `idCategoria` y el valor
     * es el número de grupos asociados a esa categoría.
     *
     * @param gruposPorCategoria Mapa que agrupa los grupos por categoría, donde la clave
     *                           es el `idCategoria` y el valor es una lista de grupos.
     * @return Un mapa donde la clave es el `idCategoria` y el valor es la cantidad de
     * grupos asociados a esa categoría.
     */
    private Map<Long, Integer> obtenerCantidadDeGruposPorCategoria(Map<Long, List<GrupoPlanificacionDTO>> gruposPorCategoria) {
        return gruposPorCategoria.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().size()));
    }


    /**
     * Ordena una lista de grupos por su orden de planificación.
     * <p>
     * Este método toma una lista de objetos `GrupoPlanificacionDTO` y los ordena de acuerdo a
     * el campo `orden` de cada objeto, utilizando un comparador. La lista resultante está
     * ordenada de menor a mayor según el valor de `orden`.
     *
     * @param grupos Lista de objetos `GrupoPlanificacionDTO` que se van a ordenar.
     * @return Una nueva lista de objetos `GrupoPlanificacionDTO` ordenada según el campo `orden`.
     */
    private List<GrupoPlanificacionDTO> ordenarGruposPorOrden(List<GrupoPlanificacionDTO> grupos) {
        return grupos.stream()
                .sorted(Comparator.comparing(GrupoPlanificacionDTO::orden))
                .collect(Collectors.toList());
    }

    /**
     * Agrupa una lista de grupos por su categoría.
     * <p>
     * Este método toma una lista de objetos `GrupoPlanificacionDTO` y la agrupa en un mapa,
     * donde la clave es el `idCategoria` de cada grupo, y el valor es una lista de grupos
     * pertenecientes a esa categoría.
     *
     * @param grupos Lista de objetos `GrupoPlanificacionDTO` que se desea agrupar.
     * @return Un mapa donde la clave es el `idCategoria` y el valor es una lista de grupos
     * pertenecientes a esa categoría.
     */
    private Map<Long, List<GrupoPlanificacionDTO>> agruparGruposPorCategoria(List<GrupoPlanificacionDTO> grupos) {
        return grupos.stream()
                .collect(Collectors.groupingBy(grupo -> grupo.idCategoria()));
    }


    /**
     * Obtiene los últimos grupos planificados para cada categoría, dados los IDs de las categorías.
     * <p>
     * Este método consulta el repositorio para obtener los últimos grupos planificados para las
     * categorías proporcionadas, luego crea un mapa donde la clave es el ID de la categoría y el valor
     * es el ID del último grupo planificado asociado a esa categoría.
     *
     * @param idsCategorias Lista de IDs de las categorías para las cuales se desean obtener los últimos grupos planificados.
     * @return Un mapa (`Map<Long, Long>`) que asocia cada ID de categoría con el ID del último grupo planificado correspondiente.
     */
    private Map<Long, Long> getUltimosGruposPorCategoriaPlanificados(List<Long> idsCategorias) {

        List<GrupoPlanificacionDTO> resultados = asignacionRepository.findUltimosGruposPorCategoriaPlanificados(idsCategorias);
        Map<Long, Long> mapa = new HashMap<>();

        for (GrupoPlanificacionDTO grupo : resultados) {
            Long grupoId = grupo.id();
            Long categoriaId = grupo.idCategoria();
            mapa.put(categoriaId, grupoId);
        }

        return mapa;
    }


    /**
     * Crea una estructura de paginación para una lista de asignaciones.
     * <p>
     * Este método toma una lista de objetos de tipo {@link AsignacionResponseDTO} y los divide en varias páginas,
     * calculando el número total de páginas y otros detalles de la paginación. La paginación se realiza con base en
     * la cantidad de elementos por página especificada.
     *
     * @param asignaciones       Lista de objetos {@link AsignacionResponseDTO} que representan las asignaciones a paginar.
     * @param elementosPorPagina Número de elementos que deben aparecer en cada página.
     * @return Una instancia de {@link PaginaDTO} que contiene las asignaciones paginadas y la información de la paginación.
     */
    private PaginaDTO<AsignacionResponseDTO> crearPaginacion(List<AsignacionResponseDTO> asignaciones, int elementosPorPagina) {
        int totalDePaginas = (int) Math.ceil((double) asignaciones.size() / elementosPorPagina);

        PaginaDTO<AsignacionResponseDTO> asignacionesDTO = new PaginaDTO<AsignacionResponseDTO>(
                asignaciones,
                new PaginaDTO.PaginacionDTO(
                        elementosPorPagina,
                        (long) asignaciones.size(),
                        totalDePaginas,
                        0,
                        totalDePaginas - 1 == 0
                )
        );
        return asignacionesDTO;
    }

    /**
     * Obtiene el índice del último grupo asignado dentro de la lista de grupos de una categoría.
     *
     * @param gruposDeLaCategoria   Lista de grupos de la categoría.
     * @param idUltimoGrupoAsignado ID del último grupo asignado.
     * @return El índice del último grupo asignado en la lista.
     * @throws RecursoNoEncontradoException Si no se encuentra el grupo con el ID proporcionado.
     */
    private int obtenerIndiceUltimoGrupoAsignado(List<GrupoPlanificacionDTO> gruposDeLaCategoria, Long idUltimoGrupoAsignado) {
        return gruposDeLaCategoria.indexOf(
                gruposDeLaCategoria.stream()
                        .filter(grupo -> grupo.id().equals(idUltimoGrupoAsignado))
                        .findFirst()
                        .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el grupo con ID: " + idUltimoGrupoAsignado))
        );
    }

    /**
     * Obtiene el orden del siguiente grupo dentro de la lista de grupos de una categoría.
     *
     * @param gruposPorCategoria Mapa que contiene las listas de grupos por categoría.
     * @param categoriaId        ID de la categoría.
     * @param siguienteGrupoId   ID del siguiente grupo cuya orden se desea obtener.
     * @return El orden del siguiente grupo.
     * @throws RecursoNoEncontradoException Si no se encuentra el grupo con el ID proporcionado.
     */
    private int obtenerOrdenSiguienteGrupo(Map<Long, List<GrupoPlanificacionDTO>> gruposPorCategoria, Long categoriaId, Long siguienteGrupoId) {
        return gruposPorCategoria.get(categoriaId).stream()
                .filter(grupo -> grupo.id().equals(siguienteGrupoId))
                .findFirst()
                .map(GrupoPlanificacionDTO::orden)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el grupo con ID: " + siguienteGrupoId));
    }


    public AsignacionInitialResponseDTO getInicialCoordinador(Pageable pageable) {

        Page<AsignacionInitialDTO> asignacionPlanificadaPage = this.asignacionRepository.findAsinacionesPlanificadas(pageable).map(this::AsignacionInitialMapper);
        Page<AsignacionInitialDTO> asignacionBorradorPage = this.asignacionRepository.findAsinacionesBorrador(pageable).map(this::AsignacionInitialMapper);

        PaginaDTO<AsignacionInitialDTO> asignacionPlanificadaDTO = new PaginaDTO<>(asignacionPlanificadaPage);
        PaginaDTO<AsignacionInitialDTO> asignacionBorradorDTO = new PaginaDTO<>(asignacionBorradorPage);

        int asignacionesTotales = asignacionPlanificadaDTO.paginacion().cantidadDeElementos().intValue() + asignacionBorradorDTO.paginacion().cantidadDeElementos().intValue();
        int planificadas = asignacionPlanificadaDTO.paginacion().cantidadDeElementos().intValue();
        int borrador = asignacionBorradorDTO.paginacion().cantidadDeElementos().intValue();

        GlobalDTO global = new GlobalDTO(asignacionesTotales, planificadas, borrador);

        return new AsignacionInitialResponseDTO(asignacionPlanificadaDTO, asignacionBorradorDTO, global);
    }

    private AsignacionInitialDTO AsignacionInitialMapper(Asignacion asignacion) {
        return new AsignacionInitialDTO(
                asignacion.getId(),
                asignacion.getGrupo().getId(),
                asignacion.getSorteo().getId(),
                asignacion.getSorteo().getProducto().getNombre(),
                asignacion.getSorteo().getProducto().getOrden(),
                asignacion.getSorteo().getFecha().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                asignacion.getEstado().getDisplayEstado(),
                asignacion.getGrupo().getNombre(),
                asignacion.getGrupo().getIntegranteList().stream()
                        .filter(integrante -> integrante.getRol().equals(Rol.AUXILIAR))
                        .map(integrante -> integrante.getNombre())
                        .collect(Collectors.toList()),
                asignacion.getGrupo().getIntegranteList().stream()
                        .filter(integrante -> integrante.getRol().equals(Rol.AUTORIDAD))
                        .map(integrante -> integrante.getNombre())
                        .collect(Collectors.toList())
        );
    }
}

