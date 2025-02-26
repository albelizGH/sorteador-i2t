package com.pentabyte.projects.sorteador.exception;

import com.pentabyte.projects.sorteador.dto.APIErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<?> handlerRecursoNoEncontradoException(Exception exception, HttpServletRequest request) {
        crearLog("Recurso no encontrado", request, exception);
        APIErrorDTO error = crearAPIErrorDTO(exception.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(Exception exception, HttpServletRequest request) {

        crearLog("Error no controlado", request, exception);

        APIErrorDTO error = crearAPIErrorDTO("Error del servidor, vuelva a intentarlo más tarde");

        return ResponseEntity.internalServerError().body(error);
    }

    private void crearLog(String mensaje, HttpServletRequest request, Exception exception) {
        log.error(mensaje + " en {}. Exception: {}", request.getRequestURL(), exception.getLocalizedMessage(), exception);
    }

    private APIErrorDTO crearAPIErrorDTO(String mensaje) {
        return new APIErrorDTO(mensaje, Instant.now());
    }
}
