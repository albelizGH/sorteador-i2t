package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.UsuarioUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.UsuarioCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.UsuarioResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.UsuarioMapper;
import com.pentabyte.projects.sorteador.model.Integrante;
import com.pentabyte.projects.sorteador.model.Usuario;
import com.pentabyte.projects.sorteador.repository.IntegranteRepository;
import com.pentabyte.projects.sorteador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Servicio que gestiona la lógica de negocio de los usuarios.
 * Proporciona operaciones CRUD para la entidad {@link Usuario}.
 */
@Service
public class UsuarioService implements CrudServiceInterface<UsuarioResponseDTO, Long, UsuarioCreateDTO, UsuarioUpdateDTO> {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final IntegranteRepository integranteRepository;
    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, UsuarioMapper usuarioMapper, IntegranteRepository integranteRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.integranteRepository = integranteRepository;
    }

    /**
     * Crea un nuevo usuario en la base de datos.
     *
     * @param dto DTO con los datos necesarios para crear al usuario.
     * @return {@link ResponseDTO} con la información del usuario.
     */
    @Override
    public ResponseDTO<UsuarioResponseDTO> crear(UsuarioCreateDTO dto) {
        Integrante integrante=this.integranteRepository.findById(dto.integranteId()).
                orElseThrow(()->new RecursoNoEncontradoException("Integrante no encontrado con ID: "+dto.integranteId()));
        Usuario usuarioDb = usuarioRepository.save(new Usuario(
                null,
                dto.usuario(),
                dto.contrasenia(),
                integrante

        ));

        UsuarioResponseDTO usuarioResponseDTO = usuarioMapper.toResponseDTO(usuarioDb);

        return new ResponseDTO<UsuarioResponseDTO>(
                usuarioResponseDTO,
                new ResponseDTO.EstadoDTO(
                        "usuario creado exitosamente",
                        "201")
        );    }

    /**
     * Actualiza un usuario existente.
     *
     * @param id  Identificador del usuario a actualizar.
     * @param dto DTO con los datos actualizados.
     * @return {@link ResponseDTO} con el usuario actualizado.
     */
    @Override
    public ResponseDTO<UsuarioResponseDTO> actualizar(Long id, UsuarioUpdateDTO dto) {
        return null;
    }

    /**
     * Hace un borrado lógico de un usuario de la base de datos.
     *
     * @param id Identificador del usuario a eliminar.
     * @return {@link ResponseDTO} indicando el estado de la operación.
     */
    @Override
    public ResponseDTO<UsuarioResponseDTO> eliminar(Long id) {
        return null;
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id Identificador único del usuario.
     * @return {@link ResponseDTO} con la información del usuario encontrada.
     * @throws RecursoNoEncontradoException si el producto no existe.
     */
    @Override
    public ResponseDTO<UsuarioResponseDTO> obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("usuario no encontrado con ID: " + id));

        return new ResponseDTO<UsuarioResponseDTO>(
                usuarioMapper.toResponseDTO(usuario),
                new ResponseDTO.EstadoDTO("usuario encontrado exitosamente", "200")
        );    }

    /**
     * Obtiene una lista paginada de todos los usuarios.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de usuarios.
     */
    @Override
    public ResponseDTO<PaginaDTO<UsuarioResponseDTO>> obtenerTodos(Pageable paginacion) {
        Page<UsuarioResponseDTO> usuarioPage = usuarioRepository.findAll(paginacion)
                .map(item -> usuarioMapper.toResponseDTO(item));

        return new ResponseDTO<PaginaDTO<UsuarioResponseDTO>>(
                new PaginaDTO<>(usuarioPage),
                new ResponseDTO.EstadoDTO("Lista de usuarios obtenida exitosamente", "200")
        );    }
}
