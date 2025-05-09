package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.PaginaDTO;
import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.GrupoUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.GrupoCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.GrupoResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GlobalDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GrupoInitialDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.GrupoInitialResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.initial.IntegranteInitialDTO;
import com.pentabyte.projects.sorteador.exception.*;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        this.categoriaTopeRepository = categoriaTopeRepository;
    }

    /**
     * Crea un nuevo grupo en la base de datos.
     *
     * @param grupoCreateDTO DTO con los datos necesarios para crear grupo.
     * @return {@link ResponseDTO} con la información de grupo creada.
     * @throws MinimoRequeridoException     si el grupo no tiene al menos un integrante.
     * @throws RecursoNoEncontradoException si el grupo, integrante o categoria  no existe.
     * @throws CupoExcedidoException        si el grupo ya tiene el cupo.maximo de integrantes segun la categoria y rol.
     */
    @Transactional
    @Override
    public ResponseDTO<GrupoResponseDTO> crear(GrupoCreateDTO grupoCreateDTO) {

        if (this.grupoRepository.existsByNombre(grupoCreateDTO.nombre())) {
            throw new YaExisteElRecursoException("El nombre del grupo ya existe");
        }

        List<Integrante> integranteList = this.integranteRepository.findAllById(grupoCreateDTO.integrantesIds());

        Categoria categoria = this.categoriaRepository.findById(grupoCreateDTO.categoriaId()).orElseThrow(() -> new RecursoNoEncontradoException("Categoria no encontrada con ID: " + grupoCreateDTO.categoriaId()));

        if (grupoCreateDTO.integrantesIds().size() != integranteList.size()) {
            throw new RecursoNoEncontradoException("Uno o mas IDs de integrantes no existen.");
        }


        Grupo grupoDb = new Grupo();
        grupoDb.setId(null);
        grupoDb.setNombre(grupoCreateDTO.nombre());
        grupoDb.setOrdenDeGrupo(obtenerUltimoOrdenGrupo() + 1);
        grupoDb.setCategoria(categoria);
        this.grupoRepository.save(grupoDb);

        for (Integrante integrante : integranteList) {

            if (integrante.getGrupo() != null)
                throw new IntegrantePertenecienteAGrupoException("Integrante con ID: " + integrante.getId() + " ya pertenece a un grupo");

            integrante.setGrupo(grupoDb);
            this.integranteRepository.save(integrante);

        }

        GrupoResponseDTO grupoResponseDTO = grupoMapper.toResponseDTO(grupoDb);
        return new ResponseDTO<GrupoResponseDTO>(
                grupoResponseDTO,
                new ResponseDTO.EstadoDTO("Grupo creado exitosamente", "200")
        );
    }

    private Integer obtenerUltimoOrdenGrupo() {
        return this.grupoRepository.obtenerUltimoOrden();
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
     */
    @Override
    public void eliminar(Long id) {

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
        Grupo grupo = grupoRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Grupo no encontrado con ID: " + id));

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
     * Agrega un integrante a un grupo específico.
     *
     * @param grupoId        Identificador del grupo al que se agregara el integrante.
     * @param grupoUpdateDTO con la informacion del grupo.
     * @return {@link ResponseDTO} con la información del grupo actualizado.
     * @throws RecursoNoEncontradoException           si grupo  o integrante no existe.
     * @throws IntegrantePertenecienteAGrupoException si el integrante ya está agregado a un grupo.
     * @throws CupoExcedidoException                  si el grupo ya tiene el cupo máximo de integrantes (Autoridades o Auxiliares) segun la categoria
     */
    @Transactional
    public ResponseDTO<GrupoResponseDTO> actualizarIntegrantesGrupo(Long grupoId, GrupoUpdateDTO grupoUpdateDTO) {

        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Grupo no encontrado con ID: " + grupoId));

        // Obtener la lista de integrantes actuales del grupo
        List<Integrante> integrantesActuales = grupo.getIntegranteList();

        // Obtener la nueva lista de integrantes por los IDs enviados
        List<Integrante> nuevosIntegrantes = integranteRepository.findAllById(grupoUpdateDTO.integrantesIds());

        if (grupoUpdateDTO.integrantesIds().size() != nuevosIntegrantes.size()) {
            throw new RecursoNoEncontradoException("Uno o más integrantes no encontrados con los IDs proporcionados: " + grupoUpdateDTO.integrantesIds());
        }

        // Determinar integrantes a eliminar (estaban antes pero ya no están en la nueva lista)
        List<Integrante> integrantesAEliminar = integrantesActuales.stream()
                .filter(integrante -> !nuevosIntegrantes.contains(integrante))
                .toList();

        // Removerlos del grupo
        for (Integrante integrante : integrantesAEliminar) {
            integrante.setGrupo(null);
            integranteRepository.save(integrante);
        }

        // Validaciones de cupo
        Integer cantidadMaximaAuxiliares = categoriaTopeRepository.obtenerCantidadMaximaPorCategoria(grupo.getCategoria().getId(), 0);
        Integer cantidadMaximaAutoridades = categoriaTopeRepository.obtenerCantidadMaximaPorCategoria(grupo.getCategoria().getId(), 1);

        Integer cantidadIntegrantesAuxiliares = categoriaTopeRepository.obtenerCantidadIntegrantes(grupo.getCategoria().getId(), "Auxiliar");
        Integer cantidadIntegrantesAutoridades = categoriaTopeRepository.obtenerCantidadIntegrantes(grupo.getCategoria().getId(), "Autoridad");

        // Asignar el grupo a los nuevos integrantes
        for (Integrante integrante : nuevosIntegrantes) {

            if (integrante.getGrupo() != null && !integrante.getGrupo().getId().equals(grupoId)) {
                throw new IntegrantePertenecienteAGrupoException("El integrante con ID: " + integrante.getId() + " ya pertenece al grupo: " + integrante.getGrupo().getNombre());
            }

            switch (integrante.getRol().getDisplaySolEstado()) {
                case "Auxiliar":
                    if (cantidadIntegrantesAuxiliares >= cantidadMaximaAuxiliares) {
                        throw new CupoExcedidoException("Cupo de integrantes auxiliares excedido");
                    }
                    cantidadIntegrantesAuxiliares++;
                    break;

                case "Autoridad":
                    if (cantidadIntegrantesAutoridades >= cantidadMaximaAutoridades) {
                        throw new CupoExcedidoException("Cupo de integrantes autoridades excedido");
                    }
                    cantidadIntegrantesAutoridades++;
                    break;

                default:
                    throw new RecursoNoEncontradoException("Rol no encontrado");
            }

            integrante.setGrupo(grupo);
            integranteRepository.save(integrante);
        }

        return new ResponseDTO<>(
                grupoMapper.toResponseDTO(grupo),
                new ResponseDTO.EstadoDTO("Lista de integrantes actualizada exitosamente", "200")
        );
    }


    public PaginaDTO<GrupoInitialDTO> getGruposCoordinador(Pageable pageable) {

        Page<GrupoInitialDTO> grupoPage = grupoRepository.findAll(pageable).map(grupo -> this.toInitialDTO(grupo));

        PaginaDTO<GrupoInitialDTO> grupoDTO = new PaginaDTO<>(grupoPage);


        return new PaginaDTO<>(grupoPage);
    }

    public List<GrupoInitialDTO> getGrupoAuxiliar(Long id) {
        Grupo grupo = this.grupoRepository.obtenerGrupoPorIntegrante(id);

        GrupoInitialDTO grupoInitialDTO = this.toInitialDTO(grupo);

        List<GrupoInitialDTO> response = List.of(grupoInitialDTO);
        return response;
    }


    public GrupoInitialResponseDTO getInicialCoordinador(Pageable pageable) {

        Page<GrupoInitialDTO> grupoPage = grupoRepository.findAll(pageable).map(grupo -> this.toInitialDTO(grupo));

        PaginaDTO<GrupoInitialDTO> grupoDTO = new PaginaDTO<>(grupoPage);

        long totales = grupoDTO.paginacion().totalDeElementos();

        GlobalDTO global = GlobalDTO.builder()
                .totales(totales)
                .build();

        return new GrupoInitialResponseDTO(global, grupoDTO.paginacion(), grupoDTO.contenido());
    }

    private GrupoInitialDTO toInitialDTO(Grupo grupo) {

        return new GrupoInitialDTO(
                grupo.getId(),
                grupo.getCategoria().getNombre(),
                grupo.getNombre(),
                grupo.getOrdenDeGrupo(),
                grupo.getIntegranteList().stream().map(integrante -> new IntegranteInitialDTO(
                        integrante.getId(),
                        integrante.getGrupo().getId(),
                        integrante.getNombre(),
                        integrante.getLegajo(),
                        integrante.getRol()
                )).collect(Collectors.toList())
        );
    }
}
