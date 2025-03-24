package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.IntegranteUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.IntegranteCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.IntegranteResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.IntegranteInitialDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.IntegranteMapper;
import com.pentabyte.projects.sorteador.model.Grupo;
import com.pentabyte.projects.sorteador.model.Integrante;
import com.pentabyte.projects.sorteador.model.Usuario;
import com.pentabyte.projects.sorteador.repository.GrupoRepository;
import com.pentabyte.projects.sorteador.repository.IntegranteRepository;
import com.pentabyte.projects.sorteador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class IntegranteService implements CrudServiceInterface<IntegranteResponseDTO, Long, IntegranteCreateDTO, IntegranteUpdateDTO> {

    private final IntegranteRepository integranteRepository;
    private final GrupoRepository grupoRepository;
    private final IntegranteMapper integranteMapper;
    private final UsuarioRepository usuarioRepository;
    @Autowired
    public IntegranteService(IntegranteRepository integranteRepository, GrupoRepository grupoRepository, IntegranteMapper integranteMapper, UsuarioRepository usuarioRepository) {
        this.integranteRepository = integranteRepository;
        this.grupoRepository = grupoRepository;
        this.integranteMapper = integranteMapper;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public ResponseDTO<IntegranteResponseDTO> crear(IntegranteCreateDTO dto) {
        Grupo grupo = grupoRepository.findById(dto.grupoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Grupo no encontrado con ID: " + dto.grupoId()));
        Usuario usuario=this.usuarioRepository.findById(dto.usuarioId())
                .orElseThrow(()->new RecursoNoEncontradoException("Usuario no encontrado con ID: "+dto.usuarioId()));
        Integrante integrante = integranteRepository.save(new Integrante(
                null,
                dto.nombre(),
                dto.legajo(),
                dto.rol(),
                grupo,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                usuario
        ));

        return new ResponseDTO<>(
                new IntegranteResponseDTO(
                        integrante.getId(),
                        integrante.getNombre(),
                        integrante.getLegajo(),
                        integrante.getRol(),
                        integrante.getGrupo().getId()
                ),
                new ResponseDTO.EstadoDTO("Integrante creado exitosamente", "201")
        );
    }

    @Override
    public ResponseDTO<IntegranteResponseDTO> actualizar(Long id, IntegranteUpdateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<IntegranteResponseDTO> eliminar(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<IntegranteResponseDTO> obtenerPorId(Long id) {
        Integrante integrante = integranteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Integrante no encontrado con ID: " + id));

        return new ResponseDTO<>(
                new IntegranteResponseDTO(
                        integrante.getId(),
                        integrante.getNombre(),
                        integrante.getLegajo(),
                        integrante.getRol(),
                        integrante.getGrupo().getId()
                ),
                new ResponseDTO.EstadoDTO("Integrante encontrado exitosamente", "200")
        );
    }

    @Override
    public ResponseDTO<PaginaDTO<IntegranteResponseDTO>> obtenerTodos(Pageable paginacion) {
        Page<IntegranteResponseDTO> integrantePage = integranteRepository.findAll(paginacion)
                .map(integrante ->
                        new IntegranteResponseDTO(
                                integrante.getId(),
                                integrante.getNombre(),
                                integrante.getLegajo(),
                                integrante.getRol(),
                                integrante.getGrupo().getId()
                        ));

        return new ResponseDTO<>(
                new PaginaDTO<>(integrantePage),
                new ResponseDTO.EstadoDTO("Lista de integrantes obtenida exitosamente", "200")
        );
    }



    public IntegranteInitialDTO getInicialByIntegranteById(Long id){

        Integrante integrante=integranteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Integrante no encontrado con ID: " + id));

        return integranteInitialMapper(integrante);
    }

    private IntegranteInitialDTO integranteInitialMapper(Integrante integrante){
        return new IntegranteInitialDTO(
                integrante.getId(),
                integrante.getGrupo().getId(),
                integrante.getNombre(),
                integrante.getLegajo(),
                integrante.getRol(),
                integrante.getUsuario().getId()
        );

    }
    }

