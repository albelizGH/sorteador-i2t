package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.config.security.SecurityContextService;
import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.SolicitudDeReemplazoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.SolicitudDeReemplazoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.IntegranteResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.SolicitudDeReemplazoResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.SorteoResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GlobalDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ReemplazoInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.ReemplazoInitialResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.SolicitudDeReemplazoMapper;
import com.pentabyte.projects.sorteador.model.*;
import com.pentabyte.projects.sorteador.repository.AsignacionRepository;
import com.pentabyte.projects.sorteador.repository.IntegranteRepository;
import com.pentabyte.projects.sorteador.repository.SolicitudDeReemplazoRepository;
import com.pentabyte.projects.sorteador.repository.SorteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Servicio que gestiona la lógica de negocio de las solicitudes de reemplazo.
 * Proporciona operaciones CRUD para la entidad {@link SolicitudDeReemplazo}.
 */
@Service
public class SolicitudDeReemplazoService implements CrudServiceInterface<SolicitudDeReemplazoResponseDTO, Long, SolicitudDeReemplazoCreateDTO, SolicitudDeReemplazoUpdateDTO> {

    private final SolicitudDeReemplazoRepository solicitudDeReemplazoRepository;
    private final IntegranteRepository integranteRepository;
    private final AsignacionRepository asignacionRepository;
    private final SorteoRepository sorteoRepository;
    private final SolicitudDeReemplazoMapper solicitudDeReemplazoMapper;
    private final SecurityContextService securityContextService;

    @Autowired
    public SolicitudDeReemplazoService(SolicitudDeReemplazoRepository solicitudDeReemplazoRepository, IntegranteRepository integranteRepository, AsignacionRepository asignacionRepository, SorteoRepository sorteoRepository, SolicitudDeReemplazoMapper solicitudDeReemplazoMapper, SecurityContextService securityContextService) {
        this.solicitudDeReemplazoRepository = solicitudDeReemplazoRepository;
        this.integranteRepository = integranteRepository;
        this.asignacionRepository = asignacionRepository;
        this.sorteoRepository = sorteoRepository;

        this.solicitudDeReemplazoMapper = solicitudDeReemplazoMapper;
        this.securityContextService = securityContextService;
    }

    @Override
    public ResponseDTO<SolicitudDeReemplazoResponseDTO> crear(SolicitudDeReemplazoCreateDTO dto) {

        Integrante empleadoSolicitante = integranteRepository.findById(dto.empleadoSolicitanteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitante no encontrado con ID: " + dto.empleadoSolicitanteId()));

        Integrante empleadoReemplazo = integranteRepository.findById(dto.empleadoReemplazoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Reemplazante no encontrado con ID: " + dto.empleadoReemplazoId()));

        Asignacion asignacionDeSolicitante = asignacionRepository.findById(dto.asignacionDeSolicitanteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Asignación de solicitante no encontrada con ID: " + dto.empleadoReemplazoId()));

        Asignacion asignacionDeReemplazo = asignacionRepository.findById(dto.asignacionDeReemplazoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Asignación de reemplazo no encontrada con ID: " + dto.empleadoReemplazoId()));


        SolicitudDeReemplazo solicitudDeReemplazoDb = solicitudDeReemplazoRepository.save(new SolicitudDeReemplazo(
                null,
                obtenerNombreSolicitudDeReemplazo(),
                dto.descripcion(),
                dto.fechaDeSolicitud(),
                SolEstado.PENDIENTE,
                empleadoSolicitante,
                empleadoReemplazo,
                asignacionDeSolicitante,
                asignacionDeReemplazo
        ));

        return new ResponseDTO<>(
                new SolicitudDeReemplazoResponseDTO(
                        solicitudDeReemplazoDb.getId(),
                        solicitudDeReemplazoDb.getDescripcion(),
                        solicitudDeReemplazoDb.getFechaDeSolicitud(),
                        solicitudDeReemplazoDb.getEstadoDeSolicitud(),
                        solicitudDeReemplazoDb.getEmpleadoSolicitante().getId(),
                        solicitudDeReemplazoDb.getEmpleadoReemplazo().getId(),
                        solicitudDeReemplazoDb.getAsignacionDeSolicitante().getId(),
                        solicitudDeReemplazoDb.getAsignacionDeReemplazo().getId()
                ),
                new ResponseDTO.EstadoDTO("Solicitud de reemplazo creada exitosamente", "201")
        );
    }

    private String obtenerNombreSolicitudDeReemplazo() {


        Optional<Long> ultimoId = this.solicitudDeReemplazoRepository.obtenerUltimoId();

        long id = ultimoId.orElse(0L);
        return "Solicitud " + (id + 1);
    }

    @Override
    public ResponseDTO<SolicitudDeReemplazoResponseDTO> actualizar(Long id, SolicitudDeReemplazoUpdateDTO dto) {
        return null; // No se realiza ninguna modificación aquí
    }

    @Override
    public void eliminar(Long id) {
        this.solicitudDeReemplazoRepository.deleteById(id);

    }

    /**
     * Obtiene una solicitud de reemplazo buscada por id.
     *
     * @param id El id de la solicitud a buscar
     * @return {@link ResponseDTO} con la solicitud encontrada.
     */
    @Override
    public ResponseDTO<SolicitudDeReemplazoResponseDTO> obtenerPorId(Long id) {
        SolicitudDeReemplazo solicitudDeReemplazo = solicitudDeReemplazoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud de reemplazo no encontrada con ID: " + id));

        return new ResponseDTO<>(
                new SolicitudDeReemplazoResponseDTO(
                        solicitudDeReemplazo.getId(),
                        solicitudDeReemplazo.getDescripcion(),
                        solicitudDeReemplazo.getFechaDeSolicitud(),
                        solicitudDeReemplazo.getEstadoDeSolicitud(),
                        solicitudDeReemplazo.getEmpleadoSolicitante().getId(),
                        solicitudDeReemplazo.getEmpleadoReemplazo().getId(),
                        solicitudDeReemplazo.getAsignacionDeSolicitante().getId(),
                        solicitudDeReemplazo.getAsignacionDeReemplazo().getId()
                ),
                new ResponseDTO.EstadoDTO("Solicitud de reemplazo encontrada exitosamente", "200")
        );
    }

    /**
     * Obtiene una lista paginada todas las solicitudes de reemplazo.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de solicitudes.
     */
    @Override
    public ResponseDTO<PaginaDTO<SolicitudDeReemplazoResponseDTO>> obtenerTodos(Pageable paginacion) {
        Page<SolicitudDeReemplazoResponseDTO> solicitudDeReemplazoPage = solicitudDeReemplazoRepository.findAll(paginacion)
                .map(solicitudDeReemplazo ->
                        new SolicitudDeReemplazoResponseDTO(
                                solicitudDeReemplazo.getId(),
                                solicitudDeReemplazo.getDescripcion(),
                                solicitudDeReemplazo.getFechaDeSolicitud(),
                                solicitudDeReemplazo.getEstadoDeSolicitud(),
                                solicitudDeReemplazo.getEmpleadoSolicitante().getId(),
                                solicitudDeReemplazo.getEmpleadoReemplazo().getId(),
                                solicitudDeReemplazo.getAsignacionDeSolicitante().getId(),
                                solicitudDeReemplazo.getAsignacionDeReemplazo().getId()
                        ));

        return new ResponseDTO<>(
                new PaginaDTO<>(solicitudDeReemplazoPage),
                new ResponseDTO.EstadoDTO("Lista de solicitudes de reemplazo obtenida exitosamente", "200")
        );
    }

    /**
     * Obtiene una lista paginada de integrantes de mismo rol y distinto grupo del id de integrante.
     *
     * @param idSolicitante Id del integrante solicitante.
     * @return {@link ResponseDTO} con la lista paginada y filtrada de integrantes.
     */
    public List<IntegranteResponseDTO> obtenerMismoRolDistintoGrupo(Long idSolicitante) {


        Integrante integranteSolicitante = integranteRepository.findById(idSolicitante)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitante no encontrado con ID: " + idSolicitante));

        LocalDateTime fecha = LocalDateTime.now();

        Rol rol = integranteSolicitante.getRol();

        String grupo = integranteSolicitante.getGrupo().getNombre();

        List<IntegranteResponseDTO> reemplazantes = integranteRepository.findReemplazantes(rol, grupo, fecha).stream().map(
                integrante -> new IntegranteResponseDTO(
                        integrante.getId(),
                        integrante.getNombre(),
                        integrante.getLegajo(),
                        integrante.getRol(),
                        integrante.getGrupo().getId()
                )
        ).toList();
        return reemplazantes;
    }

    /**
     * Recupera una lista paginada de fechas asociadas a un solicitante específico identificado por su ID.
     *
     * @param idSolicitante Id del integrante solicitante.
     * @param paginacion    Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de fechas.
     */
    public ResponseDTO<PaginaDTO<SorteoResponseDTO>> buscarFechasPorSolicitante(Long idSolicitante, Pageable paginacion) {
        Page<Sorteo> fechasPage = asignacionRepository.obtenerFechasAsignadasPorSolicitante(idSolicitante, paginacion);

        return new ResponseDTO<>(
                new PaginaDTO<>(fechasPage.map(s -> {
                    return SorteoResponseDTO.builder()
                            .id(s.getId())
                            .fecha(s.getFecha())
                            .confirmado(s.getConfirmado())
                            .diaDescriptivo(s.getDiaDescriptivo())
                            .build();
                })
                ),
                new ResponseDTO.EstadoDTO("Lista de fechas obtenida exitosamente", "200")
        );
    }

    /**
     * Recupera una lista paginada de fechas asociadas a una devolución específica identificada por su ID, filtrando por un id de sorteo.
     *
     * @param idSolicitante Id del integrante solicitante.
     * @param SorteoId      Id del sorteo que el integrante reemplazará.
     * @param paginacion    Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de fechas.
     */
    public ResponseDTO<PaginaDTO<SorteoResponseDTO>> buscarFechasParaDevolucion(Long idSolicitante, Long SorteoId, Pageable paginacion) {
        Page<Sorteo> fechasPage =
                asignacionRepository.obtenerFechasDisponiblesParaDevolucion(idSolicitante, SorteoId, paginacion);

        return new ResponseDTO<>(
                new PaginaDTO<>(fechasPage.map(s -> {
                    return SorteoResponseDTO.builder()
                            .id(s.getId())
                            .fecha(s.getFecha())
                            .confirmado(s.getConfirmado())
                            .diaDescriptivo(s.getDiaDescriptivo())
                            .build();
                })
                ),
                new ResponseDTO.EstadoDTO("Lista de fechas obtenida exitosamente", "200")
        );
    }

    /**
     * Actualiza una solicitud de reeemplazo aceptandola.
     *
     * @param solicitudId           Id de la solicitud de reemplazo.
     * @param usuarioReemplazanteId Id del usuario reemplazante.
     * @return {@link ResponseDTO} con la solicitud de reemplazo actualizada.
     */
    public ResponseDTO<SolicitudDeReemplazoResponseDTO> aceptarSolicitud(Long solicitudId, Long usuarioReemplazanteId) {
        SolicitudDeReemplazo solicitud = solicitudDeReemplazoRepository.findById(solicitudId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud de reemplazo no encontrada con ID: " + solicitudId));

        if (!solicitud.getEmpleadoReemplazo().getId().equals(usuarioReemplazanteId)) {
            throw new IllegalArgumentException("El usuario reemplazante no coincide con el solicitado.");
        }

        if (solicitud.getEstadoDeSolicitud().equals(SolEstado.CANCELADA) || solicitud.getEstadoDeSolicitud().equals(SolEstado.APROBADA)) {
            return new ResponseDTO<>(
                    new SolicitudDeReemplazoResponseDTO(
                            solicitud.getId(),
                            solicitud.getDescripcion(),
                            solicitud.getFechaDeSolicitud(),
                            solicitud.getEstadoDeSolicitud(),
                            solicitud.getEmpleadoSolicitante().getId(),
                            solicitud.getEmpleadoReemplazo().getId(),
                            solicitud.getAsignacionDeSolicitante().getId(),
                            solicitud.getAsignacionDeReemplazo().getId()
                    ),
                    new ResponseDTO.EstadoDTO("Solicitud de reemplazo cancelada/aprobada, no se puede aceptar", "400")
            );
        }

        solicitud.setEstadoDeSolicitud(SolEstado.PENDIENTE);
        solicitudDeReemplazoRepository.save(solicitud);

        return new ResponseDTO<>(
                new SolicitudDeReemplazoResponseDTO(
                        solicitud.getId(),
                        solicitud.getDescripcion(),
                        solicitud.getFechaDeSolicitud(),
                        solicitud.getEstadoDeSolicitud(),
                        solicitud.getEmpleadoSolicitante().getId(),
                        solicitud.getEmpleadoReemplazo().getId(),
                        solicitud.getAsignacionDeSolicitante().getId(),
                        solicitud.getAsignacionDeReemplazo().getId()
                ),
                new ResponseDTO.EstadoDTO("Solicitud de reemplazo aceptada exitosamente", "200")
        );
    }

    /**
     * Actualiza una solicitud de reeemplazo rechazandola.
     *
     * @param solicitudId Id de la solicitud de reemplazo.
     * @return {@link ResponseDTO} con la solicitud de reemplazo actualizada.
     */
    public ResponseDTO<SolicitudDeReemplazoResponseDTO> rechazarSolicitud(Long solicitudId) {
        SolicitudDeReemplazo solicitud = solicitudDeReemplazoRepository.findById(solicitudId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud de reemplazo no encontrada con ID: " + solicitudId));

        if (solicitud.getEstadoDeSolicitud().equals(SolEstado.APROBADA)) {
            return new ResponseDTO<>(
                    new SolicitudDeReemplazoResponseDTO(
                            solicitud.getId(),
                            solicitud.getDescripcion(),
                            solicitud.getFechaDeSolicitud(),
                            solicitud.getEstadoDeSolicitud(),
                            solicitud.getEmpleadoSolicitante().getId(),
                            solicitud.getEmpleadoReemplazo().getId(),
                            solicitud.getAsignacionDeSolicitante().getId(),
                            solicitud.getAsignacionDeReemplazo().getId()
                    ),
                    new ResponseDTO.EstadoDTO("Solicitud de reemplazo aprobada, no se puede cancelar", "400")
            );
        }

        solicitud.setEstadoDeSolicitud(SolEstado.RECHAZADA);
        solicitudDeReemplazoRepository.save(solicitud);

        return new ResponseDTO<>(
                new SolicitudDeReemplazoResponseDTO(
                        solicitud.getId(),
                        solicitud.getDescripcion(),
                        solicitud.getFechaDeSolicitud(),
                        solicitud.getEstadoDeSolicitud(),
                        solicitud.getEmpleadoSolicitante().getId(),
                        solicitud.getEmpleadoReemplazo().getId(),
                        solicitud.getAsignacionDeSolicitante().getId(),
                        solicitud.getAsignacionDeReemplazo().getId()
                ),
                new ResponseDTO.EstadoDTO("Solicitud de reemplazo rechazada", "200")
        );
    }

    /**
     * Actualiza una solicitud de reeemplazo aprobandola.
     *
     * @param solicitudId Id de la solicitud de reemplazo.
     * @return {@link ResponseDTO} con la solicitud de reemplazo actualizada.
     */
    public ResponseDTO<SolicitudDeReemplazoResponseDTO> aprobarSolicitud(Long solicitudId) {
        SolicitudDeReemplazo solicitud = solicitudDeReemplazoRepository.findById(solicitudId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitud de reemplazo no encontrada con ID: " + solicitudId));

        if (solicitud.getEstadoDeSolicitud().equals(SolEstado.CANCELADA)) {
            return new ResponseDTO<>(
                    new SolicitudDeReemplazoResponseDTO(
                            solicitud.getId(),
                            solicitud.getDescripcion(),
                            solicitud.getFechaDeSolicitud(),
                            solicitud.getEstadoDeSolicitud(),
                            solicitud.getEmpleadoSolicitante().getId(),
                            solicitud.getEmpleadoReemplazo().getId(),
                            solicitud.getAsignacionDeSolicitante().getId(),
                            solicitud.getAsignacionDeReemplazo().getId()
                    ),
                    new ResponseDTO.EstadoDTO("Solicitud de reemplazo cancelada, no se puede aprobar", "400")
            );
        }

        solicitud.setEstadoDeSolicitud(SolEstado.APROBADA);
        solicitudDeReemplazoRepository.save(solicitud);

        return new ResponseDTO<>(
                new SolicitudDeReemplazoResponseDTO(
                        solicitud.getId(),
                        solicitud.getDescripcion(),
                        solicitud.getFechaDeSolicitud(),
                        solicitud.getEstadoDeSolicitud(),
                        solicitud.getEmpleadoSolicitante().getId(),
                        solicitud.getEmpleadoReemplazo().getId(),
                        solicitud.getAsignacionDeSolicitante().getId(),
                        solicitud.getAsignacionDeReemplazo().getId()
                ),
                new ResponseDTO.EstadoDTO("Solicitud de reemplazo aprobada exitosamente", "200")
        );
    }

    public ReemplazoInitialResponseDTO getIncialReemplazosAuxiliar(Pageable pageable) {
        Long id = this.securityContextService.getIdDeUsuarioDesdeAuthenticated();

        PaginaDTO<ReemplazoInitialDTO> solicitudesNoPendientes = this.getReemplazosNoPendientesAuxiliar(pageable, id);
        PaginaDTO<ReemplazoInitialDTO> solicitudesPendientes = this.getReemplazosPendientesAuxiliar(pageable, id);

        GlobalDTO globalDTO = GlobalDTO.builder()
                .pendientes(solicitudesPendientes.paginacion().totalDeElementos())
                .noPendientes(solicitudesNoPendientes.paginacion().totalDeElementos())
                .build();

        return new ReemplazoInitialResponseDTO(
                globalDTO,
                solicitudesPendientes.contenido(),
                solicitudesNoPendientes.contenido(),
                solicitudesPendientes.paginacion(),
                solicitudesNoPendientes.paginacion()
        );

    }


    public PaginaDTO<ReemplazoInitialDTO> getReemplazosPendientesAuxiliar(Pageable pageable, Long id) {
        Page<ReemplazoInitialDTO> reemplazosPage = solicitudDeReemplazoRepository.findAllPendientesPorSolicitante(pageable, id)
                .map(this::toInitialDTO);
        return new PaginaDTO<>(reemplazosPage);
    }

    public PaginaDTO<ReemplazoInitialDTO> getReemplazosNoPendientesAuxiliar(Pageable pageable, Long id) {
        Page<ReemplazoInitialDTO> reemplazosPage = solicitudDeReemplazoRepository.findAllNoPendientesPorSolicitante(pageable, id)
                .map(this::toInitialDTO);
        return new PaginaDTO<>(reemplazosPage);
    }

    public ReemplazoInitialResponseDTO getIncialReemplazosCoordinador(Pageable pageable) {

        PaginaDTO<ReemplazoInitialDTO> solicitudesNoPendientes = this.getReemplazosNoPendientesCoordinador(pageable);

        PaginaDTO<ReemplazoInitialDTO> solicitudesPendientes = this.getReemplazosPendientesCoordinador(pageable);


        GlobalDTO globalDTO = GlobalDTO.builder()
                .pendientes(solicitudDeReemplazoRepository.countReemplazosPendientes())
                .noPendientes(solicitudDeReemplazoRepository.countReemplazosNoPendientes())
                .build();

        return new ReemplazoInitialResponseDTO(
                globalDTO,
                solicitudesPendientes.contenido(),
                solicitudesNoPendientes.contenido(),
                solicitudesPendientes.paginacion(),
                solicitudesNoPendientes.paginacion()

        );
    }

    public PaginaDTO<ReemplazoInitialDTO> getReemplazosPendientesCoordinador(Pageable pageable) {
        Page<ReemplazoInitialDTO> reemplazosPage = solicitudDeReemplazoRepository.findAllPendientes(pageable)
                .map(this::toInitialDTO);
        return new PaginaDTO<>(reemplazosPage);
    }

    public PaginaDTO<ReemplazoInitialDTO> getReemplazosNoPendientesCoordinador(Pageable pageable) {
        Page<ReemplazoInitialDTO> reemplazosPage = solicitudDeReemplazoRepository.findAllNoPendientes(pageable)
                .map(this::toInitialDTO);
        return new PaginaDTO<>(reemplazosPage);
    }

    private ReemplazoInitialDTO toInitialDTO(SolicitudDeReemplazo solicitud) {
        return new ReemplazoInitialDTO(
                solicitud.getId(),
                solicitud.getEmpleadoSolicitante().getNombre(),
                solicitud.getEmpleadoReemplazo().getNombre(),
                solicitud.getDescripcion(),
                solicitud.getFechaDeSolicitud(),
                solicitud.getAsignacionDeSolicitante().getSorteo().getFecha().toLocalDate(),
                solicitud.getAsignacionDeSolicitante().getSorteo().getProducto().getNombre(),
                solicitud.getEmpleadoSolicitante().getId(),
                solicitud.getEmpleadoReemplazo().getId(),
                solicitud.getEstadoDeSolicitud(),
                solicitud.getAsignacionDeSolicitante().getId(),
                solicitud.getAsignacionDeReemplazo().getId());
    }

    public ResponseDTO<ReemplazoInitialDTO> crearSolicitud(SolicitudDeReemplazoCreateDTO dto) {

        Integrante empleadoSolicitante = integranteRepository.findById(dto.empleadoSolicitanteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitante no encontrado con ID: " + dto.empleadoSolicitanteId()));

        Integrante empleadoReemplazo = integranteRepository.findById(dto.empleadoReemplazoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Reemplazante no encontrado con ID: " + dto.empleadoReemplazoId()));

        Asignacion asignacionDeSolicitante = asignacionRepository.findById(dto.asignacionDeSolicitanteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Asignación de solicitante no encontrada con ID: " + dto.empleadoReemplazoId()));

        Asignacion asignacionDeReemplazo = asignacionRepository.findById(dto.asignacionDeReemplazoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Asignación de reemplazo no encontrada con ID: " + dto.empleadoReemplazoId()));


        SolicitudDeReemplazo solicitudDeReemplazoDb = solicitudDeReemplazoRepository.save(new SolicitudDeReemplazo(
                null,
                obtenerNombreSolicitudDeReemplazo(),
                dto.descripcion(),
                dto.fechaDeSolicitud(),
                SolEstado.PENDIENTE,
                empleadoSolicitante,
                empleadoReemplazo,
                asignacionDeSolicitante,
                asignacionDeReemplazo
        ));

        return new ResponseDTO<>(
                new ReemplazoInitialDTO(
                        solicitudDeReemplazoDb.getId(),
                        solicitudDeReemplazoDb.getEmpleadoSolicitante().getNombre(),
                        solicitudDeReemplazoDb.getEmpleadoReemplazo().getNombre(),
                        solicitudDeReemplazoDb.getDescripcion(),
                        solicitudDeReemplazoDb.getFechaDeSolicitud(),
                        solicitudDeReemplazoDb.getAsignacionDeSolicitante().getSorteo().getFecha().toLocalDate(),
                        solicitudDeReemplazoDb.getAsignacionDeSolicitante().getSorteo().getProducto().getNombre(),
                        solicitudDeReemplazoDb.getEmpleadoSolicitante().getId(),
                        solicitudDeReemplazoDb.getEmpleadoReemplazo().getId(),
                        solicitudDeReemplazoDb.getEstadoDeSolicitud(),
                        solicitudDeReemplazoDb.getAsignacionDeSolicitante().getId(),
                        solicitudDeReemplazoDb.getAsignacionDeReemplazo().getId()
                ),
                new ResponseDTO.EstadoDTO("Solicitud de reemplazo creada exitosamente", "201")
        );
    }

}
