package com.pentabyte.projects.sorteador.service;

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

    @Autowired
    public SolicitudDeReemplazoService(SolicitudDeReemplazoRepository solicitudDeReemplazoRepository, IntegranteRepository integranteRepository, AsignacionRepository asignacionRepository, SorteoRepository sorteoRepository, SolicitudDeReemplazoMapper solicitudDeReemplazoMapper) {
        this.solicitudDeReemplazoRepository = solicitudDeReemplazoRepository;
        this.integranteRepository = integranteRepository;
        this.asignacionRepository = asignacionRepository;
        this.sorteoRepository = sorteoRepository;

        this.solicitudDeReemplazoMapper = solicitudDeReemplazoMapper;
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
                dto.nombre(),
                dto.descripcion(),
                dto.fechaDeSolicitud(),
                dto.estadoDeSolicitud(),
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

    @Override
    public ResponseDTO<SolicitudDeReemplazoResponseDTO> actualizar(Long id, SolicitudDeReemplazoUpdateDTO dto) {
        return null; // No se realiza ninguna modificación aquí
    }

    @Override
    public ResponseDTO<SolicitudDeReemplazoResponseDTO> eliminar(Long id) {
        return null; // No se realiza ninguna modificación aquí
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
     * @param paginacion    Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada y filtrada de integrantes.
     */
    public ResponseDTO<PaginaDTO<IntegranteResponseDTO>> obtenerMismoRolDistintoGrupo(Long idSolicitante, Pageable paginacion) {
        Integrante integranteSolicitante = integranteRepository.findById(idSolicitante)
                .orElseThrow(() -> new RecursoNoEncontradoException("Solicitante no encontrado con ID: " + idSolicitante));

        Rol rol = integranteSolicitante.getRol();
        String grupo = integranteSolicitante.getGrupo().getNombre();

        Page<Integrante> reemplazantes = integranteRepository.findReemplazantes(rol, grupo, paginacion);

        return new ResponseDTO<>(
                new PaginaDTO<>(reemplazantes.map(c -> {
                    return IntegranteResponseDTO.builder()
                            .id(c.getId())
                            .nombre(c.getNombre())
                            .legajo(c.getLegajo())
                            .rol(c.getRol())
                            .grupoId(c.getGrupo().getId())
                            .build();
                })),
                new ResponseDTO.EstadoDTO("Lista de reemplazantes obtenida exitosamente", "200")
        );
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

        solicitud.setEstadoDeSolicitud(SolEstado.CANCELADA);
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

    public ReemplazoInitialResponseDTO getIncialReemplazosCoordinador(Pageable pageable) {

        Page<ReemplazoInitialDTO> solicitudDeReemplazosPendientesPage = solicitudDeReemplazoRepository.findAllConEstado(pageable, SolEstado.PENDIENTE)
                .map(this::toInitialDTO);

        Page<ReemplazoInitialDTO> solicitudesNoPendientesPage = solicitudDeReemplazoRepository.findAllNoPendientes(pageable)
                .map(this::toInitialDTO);

        PaginaDTO<ReemplazoInitialDTO> solicitudesNoPendientes = new PaginaDTO<>(solicitudesNoPendientesPage);

        PaginaDTO<ReemplazoInitialDTO> solicitudesPendientes = new PaginaDTO<>(solicitudDeReemplazosPendientesPage);


        GlobalDTO globalDTO = GlobalDTO.builder()
                .pendientes(Math.toIntExact(solicitudesNoPendientes.paginacion().totalDeElementos()))
                .noPendientes(Math.toIntExact(solicitudesPendientes.paginacion().totalDeElementos()))
                .build();

        return new ReemplazoInitialResponseDTO(
                globalDTO,
                solicitudesPendientes,
                solicitudesNoPendientes
        );
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
}
