package com.pentabyte.projects.sorteador.service;

import java.util.List;

public interface ICrudService<T, ID, Dc, Du> {
    /*
     * Interface CRUD en común para todos los servicios
     * Usamos T como un DTO de salida creado con un patron builder para poder tener solo un DTO de salida por cada entidad
     * Usamos Dc como un DTO de entrada para creación
     * Usamos Du como un DTO de entrada para actualización
     * */

    T create(Dc dto);

    T update(ID id, Du dto);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();


}
