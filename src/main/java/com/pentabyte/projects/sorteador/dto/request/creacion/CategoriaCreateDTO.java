package com.pentabyte.projects.sorteador.dto.request.creacion;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CategoriaCreateDTO(

        @NotBlank(message = "El nombre no puede estar vacío")
        String nombre,

        @Min(value = 1, message = "La última semana de asignación debe ser mayor que 0")
        Integer ultimaSemanaDeAsignacion,

        LocalDate ultimaFechaDeAsignacion,

        @NotNull(message = "El número de semanas a planificar no puede ser nulo")
        @Min(value = 1, message = "El número de semanas a planificar debe ser mayor que 0")
        Integer semanasAPlanificar,

        @NotNull(message = "La lista de tope de categorias no puede ser nula")
        List<CategoriaTopeCreateDTO> categoriaTopeList,

        @NotNull(message = "La lista de grupos no puede ser nula")
        List<Long> grupoList,

        @NotNull(message = "La lista de tope de productos no puede ser nula")
        List<Long>productoList
) {
}
