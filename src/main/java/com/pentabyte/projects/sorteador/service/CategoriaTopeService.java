package com.pentabyte.projects.sorteador.service;

import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import com.pentabyte.projects.sorteador.dto.request.actualizacion.CategoriaTopeUpdateDTO;
import com.pentabyte.projects.sorteador.dto.request.creacion.CategoriaTopeCreateDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaResponseDTO;
import com.pentabyte.projects.sorteador.dto.response.CategoriaTopeResponseDTO;
import com.pentabyte.projects.sorteador.exception.RecursoNoEncontradoException;
import com.pentabyte.projects.sorteador.interfaces.CrudServiceInterface;
import com.pentabyte.projects.sorteador.model.CategoriaTope;
import com.pentabyte.projects.sorteador.repository.CategoriaTopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoriaTopeService implements CrudServiceInterface<CategoriaTopeResponseDTO, Long, CategoriaTopeCreateDTO, CategoriaTopeUpdateDTO> {

    private final CategoriaTopeRepository categoriaTopeRepository;

    @Autowired
    public CategoriaTopeService(CategoriaTopeRepository categoriaTopeRepository) {
        this.categoriaTopeRepository = categoriaTopeRepository;
    }

    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> crear(CategoriaTopeCreateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> actualizar(Long id, CategoriaTopeUpdateDTO dto) {
        return null;
    }

    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> eliminar(Long id) {
        return null;
    }

    @Override
    public ResponseDTO<CategoriaTopeResponseDTO> obtenerPorId(Long id) {
        CategoriaTope categoriaTope = categoriaTopeRepository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Categoría tope no encontrada con ID: " + id));
        ;
        return new ResponseDTO<CategoriaTopeResponseDTO>(
                new CategoriaTopeResponseDTO(categoriaTope.getId(),
                        new CategoriaResponseDTO(
                                categoriaTope.getCategoria().getId(),
                                categoriaTope.getCategoria().getNombre(),
                                categoriaTope.getCategoria().getUltimaSemanaDeAsignacion(),
                                categoriaTope.getCategoria().getUltimaFechaDeAsignacion(),
                                categoriaTope.getCategoria().getSemanasAPlanificar()
                        ),
                        categoriaTope.getCantidadMinima(),
                        categoriaTope.getCantidadMaxima(),
                        categoriaTope.getEsAutoridad()),
                new ResponseDTO.EstadoDTO("Categoría tope encontrada exitosamente", HttpStatus.OK)
        );
    }

    @Override
    public ResponseDTO<Page<CategoriaTopeResponseDTO>> obtenerTodos(Pageable paginacion) {
        Page<CategoriaTopeResponseDTO> categoriaTopesPage = categoriaTopeRepository.findAll(paginacion)
                .map(item -> new CategoriaTopeResponseDTO(
                        item.getId(),
                        new CategoriaResponseDTO(
                                item.getCategoria().getId(),
                                item.getCategoria().getNombre(),
                                item.getCategoria().getUltimaSemanaDeAsignacion(),
                                item.getCategoria().getUltimaFechaDeAsignacion(),
                                item.getCategoria().getSemanasAPlanificar()
                        ),
                        item.getCantidadMinima(),
                        item.getCantidadMaxima(),
                        item.getEsAutoridad()
                ));

        return new ResponseDTO<>(
                categoriaTopesPage,
                new ResponseDTO.EstadoDTO("Lista de categorías topes obtenida exitosamente", HttpStatus.OK)
        );
    }

}
