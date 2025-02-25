package com.pentabyte.projects.sorteador.dto;

/**
 * Record genérico para responder peticiones HTTP.
 *
 * @param <T>    Tipo de dato de la respuesta.
 * @param data   Contenido de la respuesta, limitado a tipos que sean records.
 * @param estado Estado de la respuesta, que incluye un mensaje y un código.
 */
public record ResponseDTO<T>(T data, EstadoDTO estado) {

    /**
     * Record interno para representar el estado de la respuesta.
     * Contiene un mensaje descriptivo y un código asociado al estado.
     *
     * @param mensaje Descripción del estado de la respuesta.
     * @param codigo  Código asociado al estado de la respuesta.
     */
    public record EstadoDTO(String mensaje, String codigo) {
    }
}
