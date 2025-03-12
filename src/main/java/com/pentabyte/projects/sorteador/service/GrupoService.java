package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.GrupoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.GrupoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.GrupoResponseDTO;
import com.pentabyte.projects.sorteador.exception.CupoExcedidoException;
import com.pentabyte.projects.sorteador.exception.IntegrantePertenecienteAGrupo;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.mapper.GrupoMapper;
import com.pentabyte.projects.sorteador.model.Categoria;
import com.pentabyte.projects.sorteador.model.Grupo;
import com.pentabyte.projects.sorteador.model.Integrante;
import com.pentabyte.projects.sorteador.repository.CategoriaRepository;
import com.pentabyte.projects.sorteador.repository.CategoriaTopeRepository;
import com.pentabyte.projects.sorteador.repository.GrupoRepository;
import com.pentabyte.projects.sorteador.repository.IntegranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Servicio que gestiona la lógica de negocio de los grupos.
 * Proporciona operaciones CRUD para la entidad {@link Grupo}.
 */
@Service
public class GrupoService implements CrudServiceInterface<GrupoResponseDTO, Long, GrupoCreateDTO, GrupoUpdateDTO> {
    private final GrupoRepository grupoRepository;
    private final GrupoMapper grupoMapper;
    private final CategoriaRepository categoriaRepository;
     private final IntegranteRepository integranteRepository;
     private final CategoriaTopeRepository categoriaTopeRepository;
    @Autowired
    public GrupoService(
            GrupoRepository grupoRepository,
            GrupoMapper grupoMapper,
            CategoriaRepository categoriaRepository,
            IntegranteRepository integranteRepository,
            CategoriaTopeRepository categoriaTopeRepository) {
        this.grupoRepository = grupoRepository;
        this.grupoMapper = grupoMapper;
        this.categoriaRepository = categoriaRepository;
        this.integranteRepository = integranteRepository;
        this.categoriaTopeRepository=categoriaTopeRepository;
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

    /**
     * Asigna un integrante a un grupo específico.
     *
     * @param grupoId Identificador del grupo al que se asignará el integrante.
     * @param integranteId Identificador del integrante que se asignará al grupo.
     * @return {@link ResponseDTO} con la información del grupo actualizado.
     * @throws RecursoNoEncontradoException si grupo  o integrante no existe.
     * @throws IntegrantePertenecienteAGrupo si el integrante ya está asignado al grupo.
     * @throws CupoExcedidoException si el grupo ya tiene el cupo máximo de integrantes segun la categoria
     */
public ResponseDTO<GrupoResponseDTO> asignarIntegranteAGrupo(Long grupoId,Long integranteId){

    Grupo grupo=grupoRepository.findById(grupoId).orElseThrow(()->new RecursoNoEncontradoException("Grupo no encontrado con ID: "+grupoId));

    Integrante integrante=integranteRepository.findById(integranteId).orElseThrow(()->new RecursoNoEncontradoException("Integrante no encontrado con ID: "+integranteId));

    if (integrante.getGrupo()!=null) throw new IntegrantePertenecienteAGrupo("Integrante con ID: "+integranteId+" ya pertenece a un grupo");

    if (integrante.getRol().equals("Auxiliar")){
            Integer cantidadIntegrantesAuxiliaresAsignados= categoriaTopeRepository.obtenerCantidadIntegrantesAuxiliaresAsignados(grupo.getCategoria().getId());

            if (cantidadIntegrantesAuxiliaresAsignados>categoriaTopeRepository.obtenerCantidadMaximaAuxiliarPorCategoria(grupo.getCategoria().getId())){
                throw new CupoExcedidoException("Cupo de integrantes auxiliares excedido");

            }else{
                integrante.setGrupo(grupo);
                integranteRepository.save(integrante);
            }
    }

    if (integrante.getRol().equals("Autoridad")) {
        Integer cantidadIntegrantesAutoridadesAsignados = categoriaTopeRepository.obtenerCantidadIntegrantesAutoridadesAsignados(grupo.getCategoria().getId());

        if (cantidadIntegrantesAutoridadesAsignados > categoriaTopeRepository.obtenerCantidadMaximaAutoridadPorCategoria(grupo.getCategoria().getId())) {
            throw new CupoExcedidoException("Cupo de integrantes autoridades excedido");

        } else {
            integrante.setGrupo(grupo);
            integranteRepository.save(integrante);
        }
    }
    return new ResponseDTO<GrupoResponseDTO>(
            grupoMapper.toResponseDTO(grupo),
            new ResponseDTO.EstadoDTO("Integrante asignado exitosamente","200")

    );

}

}
