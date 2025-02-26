package com.pentabyte.projects.sorteador.interfaces;

import com.pentabyte.projects.sorteador.dto.ResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Interfaz genérica CRUD para los servicios.
 *
 * @param <T>  Tipo del DTO de salida (debe ser un record).
 * @param <ID> Tipo del identificador de la entidad.
 * @param <Dc> Tipo del DTO de entrada para creación.
 * @param <Du> Tipo del DTO de entrada para actualización.
 */
public interface CrudServiceInterface<T extends Record, ID, Dc, Du> {

    /**
     * Crea una nueva entidad a partir del DTO de creación.
     *
     * @param dto DTO de entrada con los datos para la creación.
     * @return ResponseDTO con el objeto creado.
     */
    ResponseDTO<T> crear(Dc dto);

    /**
     * Actualiza una entidad existente.
     *
     * @param id  Identificador de la entidad a actualizar.
     * @param dto DTO de entrada con los datos actualizados.
     * @return ResponseDTO con el objeto actualizado.
     */
    ResponseDTO<T> actualizar(ID id, Du dto);

    /**
     * Elimina una entidad por su identificador.
     *
     * @param id Identificador de la entidad a eliminar.
     * @return ResponseDTO con la entidad eliminada o una confirmación.
     */
    ResponseDTO<T> eliminar(ID id);

    /**
     * Busca una entidad por su identificador.
     *
     * @param id Identificador de la entidad a buscar.
     * @return ResponseDTO con la entidad encontrada.
     */
    ResponseDTO<T> obtenerPorId(ID id);

    /**
     * Obtiene todas las entidades disponibles.
     *
     * @return ResponseDTO con una lista de entidades.
     */
    ResponseDTO<Page<T>> obtenerTodos(Pageable paginacion);
}
