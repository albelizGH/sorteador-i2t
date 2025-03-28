package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.config.security.AuthenticationResponseDTO;
import com.pentabyte.projects.sorteador.config.security.CredencialesDTO;
import com.pentabyte.projects.sorteador.config.security.JwtService;
import com.pentabyte.projects.sorteador.config.security.UserDetail;
import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.IntegranteUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.IntegranteCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.IntegranteResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.IntegranteInitialDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.IntegranteMapper;
import com.pentabyte.projects.sorteador.model.Integrante;
import com.pentabyte.projects.sorteador.repository.GrupoRepository;
import com.pentabyte.projects.sorteador.repository.IntegranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IntegranteService implements CrudServiceInterface<IntegranteResponseDTO, Long, IntegranteCreateDTO, IntegranteUpdateDTO> {

    private final IntegranteRepository integranteRepository;
    private final GrupoRepository grupoRepository;
    private final IntegranteMapper integranteMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public IntegranteService(IntegranteRepository integranteRepository, GrupoRepository grupoRepository, IntegranteMapper integranteMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.integranteRepository = integranteRepository;
        this.grupoRepository = grupoRepository;
        this.integranteMapper = integranteMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public ResponseDTO<IntegranteResponseDTO> crear(IntegranteCreateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<IntegranteResponseDTO> actualizar(Long id, IntegranteUpdateDTO dto) {
        return null;
    }

    /**
     * Hace un borrado lógico de una INTEGRANTE de la base de datos.
     *
     * @param id Identificador de integrante a eliminar.
     */
    @Override
    public void eliminar(Long id) {

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

    public List<IntegranteResponseDTO> obtenerTodosIntegrantesSinGrupo() {

        List<Integrante> integrantes = integranteRepository.findByGrupoIsNull();

        return integrantes.stream()
                .map(integrante -> IntegranteResponseDTO.builder()
                        .id(integrante.getId())
                        .nombre(integrante.getNombre())
                        .rol(integrante.getRol())
                        .legajo(integrante.getLegajo())
                        .build())
                .collect(Collectors.toList());

    }

    public IntegranteInitialDTO getIntegranteByIdCoordinador(Long id) {

        Integrante integrante = integranteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Integrante no encontrado con ID: " + id));

        return toInitialDTO(integrante);
    }

    public IntegranteInitialDTO getInicialIntegranteByIdCoordinador(Long id) {

        Integrante integrante = integranteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Integrante no encontrado con ID: " + id));

        return toInitialDTO(integrante);
    }

    private IntegranteInitialDTO toInitialDTO(Integrante integrante) {
        return new IntegranteInitialDTO(
                integrante.getId(),
                integrante.getGrupo().getId(),
                integrante.getNombre(),
                integrante.getLegajo(),
                integrante.getRol()
        );

    }


    /**
     * Obtiene una lista paginada de todos los usuarios.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de usuarios.
     */
    @Override
    public ResponseDTO<PaginaDTO<IntegranteResponseDTO>> obtenerTodos(Pageable paginacion) {
        Page<IntegranteResponseDTO> integrante = integranteRepository.findAll(paginacion)
                .map(item -> integranteMapper.toResponseDTO(item));

        return new ResponseDTO<PaginaDTO<IntegranteResponseDTO>>(
                new PaginaDTO<>(integrante),
                new ResponseDTO.EstadoDTO("Lista de usuarios obtenida exitosamente", "200")
        );
    }

    public Integrante registrarUsuario(IntegranteCreateDTO dto) {

        Integrante integrante = new Integrante(
                null,
                dto.nombre(),
                dto.legajo(),
                dto.rol(),
                dto.email(),
                passwordEncoder.encode(dto.password()),
                null,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );

        return integranteRepository.save(integrante);
    }

    public AuthenticationResponseDTO login(CredencialesDTO credenciales) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(credenciales.email(), credenciales.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        UserDetail user = (UserDetail) authentication.getPrincipal();
        String jwt = jwtService.generateToken(authentication);
        return new AuthenticationResponseDTO(jwt, user.getUsuario().getRol());
    }
}

