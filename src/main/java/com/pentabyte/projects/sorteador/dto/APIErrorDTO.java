package com.pentabyte.projects.sorteador.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record APIErrorDTO(String error, List<String> errores, LocalDateTime timestamp) {

    public APIErrorDTO(String error) {
        this(error, null, LocalDateTime.now());
    }

    public APIErrorDTO(List<String> errores) {
        this(null, errores, LocalDateTime.now());
    }
}
