package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.GrupoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.GrupoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.GrupoResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.GrupoMapper;
import com.pentabyte.projects.sorteador.model.Categoria;
import com.pentabyte.projects.sorteador.model.Grupo;
import com.pentabyte.projects.sorteador.repository.CategoriaRepository;
import com.pentabyte.projects.sorteador.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Servicio que gestiona la lógica de negocio de los grupos.
 * Proporciona operaciones CRUD para la entidad {@link Grupo}.
 */
@Service
public class GrupoService implements CrudServiceInterface<GrupoResponseDTO, Long, GrupoCreateDTO, GrupoUpdateDTO> {
    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public GrupoService(GrupoRepository grupoRepository, GrupoMapper grupoMapper, CategoriaRepository categoriaRepository) {
        this.grupoRepository = grupoRepository;
        this.grupoMapper = grupoMapper;
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Crea un nuevo grupo en la base de datos.
     *
     * @param dto DTO con los datos necesarios para crear grupo.
     * @return {@link ResponseDTO} con la información de grupo creada.
     * @throws RecursoNoEncontradoException si el grupo asociado no existe.
     */

    @Override
    public ResponseDTO<GrupoResponseDTO> crear(GrupoCreateDTO dto) {

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Categoría no encontrada con ID: " + dto.categoriaId()));

        Grupo grupoDb = grupoRepository.save(new Grupo(
                null,
                dto.nombre(),
                dto.ordenDeGrupo(),
                categoria,
                new ArrayList<>(),
                new ArrayList<>()

        ));
        
        GrupoResponseDTO grupoResponseDTO = grupoMapper.toResponseDTO(grupoDb);

        return new ResponseDTO<GrupoResponseDTO>(
                grupoResponseDTO,
                new ResponseDTO.EstadoDTO(
                        "Grupo creado exitosamente",
                        "201")
        );
    }

    /**
     * Actualiza un grupo existente.
     *
     * @param id  Identificador de grupo a actualizar.
     * @param dto DTO con los datos actualizados.
     * @return {@link ResponseDTO} con la información de grupo actualizada.
     */
    @Override
    public ResponseDTO<GrupoResponseDTO> actualizar(Long id, GrupoUpdateDTO dto) {
        return null;
    }

    /**
     * Hace un borrado lógico de un grupo de la base de datos.
     *
     * @param id Identificador de grupo a eliminar.
     * @return {@link ResponseDTO} indicando el estado de la operación.
     */
    @Override
    public ResponseDTO<GrupoResponseDTO> eliminar(Long id) {
        return null;
    }


    /**
     * Obtiene un grupo por su ID.
     *
     * @param id Identificador único de grupo.
     * @return {@link ResponseDTO} con la información de grupo encontrada.
     * @throws RecursoNoEncontradoException si grupo no existe.
     */
    @Override
    public ResponseDTO<GrupoResponseDTO> obtenerPorId(Long id) {
        Grupo grupo = grupoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Grupo no encontrado con ID: " + id));

        return new ResponseDTO<GrupoResponseDTO>(
                grupoMapper.toResponseDTO(grupo),
                new ResponseDTO.EstadoDTO("Grupo encontrado exitosamente", "200")
        );
    }

    /**
     * Obtiene una lista paginada de todas los grupos.
     *
     * @param paginacion Objeto de paginación proporcionado por Spring.
     * @return {@link ResponseDTO} con la lista paginada de grupo.
     */
    @Override
    public ResponseDTO<PaginaDTO<GrupoResponseDTO>> obtenerTodos(Pageable paginacion) {

        Page<GrupoResponseDTO> grupoPage = grupoRepository.findAll(paginacion)
                .map(item -> grupoMapper.toResponseDTO(item));

        return new ResponseDTO<PaginaDTO<GrupoResponseDTO>>(
                new PaginaDTO<>(grupoPage),
                new ResponseDTO.EstadoDTO("Lista de grupos obtenida exitosamente", "200")
        );
    }

}
