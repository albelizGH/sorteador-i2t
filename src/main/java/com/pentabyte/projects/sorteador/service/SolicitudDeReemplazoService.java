package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.SolicitudDeReemplazoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.SolicitudDeReemplazoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.SolicitudDeReemplazoResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.SolicitudDeReemplazoMapper;
import com.pentabyte.projects.sorteador.model.Asignacion;
import com.pentabyte.projects.sorteador.model.Integrante;
import com.pentabyte.projects.sorteador.model.SolicitudDeReemplazo;
import com.pentabyte.projects.sorteador.repository.AsignacionRepository;
import com.pentabyte.projects.sorteador.repository.GrupoRepository;
import com.pentabyte.projects.sorteador.repository.IntegranteRepository;
import com.pentabyte.projects.sorteador.repository.SolicitudDeReemplazoRepository;
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
    private final SolicitudDeReemplazoMapper solicitudDeReemplazoMapper;

    @Autowired
    public SolicitudDeReemplazoService(SolicitudDeReemplazoRepository solicitudDeReemplazoRepository, GrupoRepository grupoRepository, IntegranteRepository integranteRepository, AsignacionRepository asignacionRepository, SolicitudDeReemplazoMapper solicitudDeReemplazoMapper) {
        this.solicitudDeReemplazoRepository = solicitudDeReemplazoRepository;
        this.integranteRepository = integranteRepository;
        this.asignacionRepository = asignacionRepository;
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
}
