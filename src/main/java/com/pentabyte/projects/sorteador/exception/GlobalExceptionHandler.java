package com.pentabyte.projects.sorteador.exception;

import com.pentabyte.projects.sorteador.dto.APIErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handlerValidation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        crearLog("Hay validaciones que no se cumplen", request, exception);

        List<String> listaDeErrores = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toUnmodifiableList());

        APIErrorDTO error;
        if (listaDeErrores.size() == 1) {
            error = new APIErrorDTO(listaDeErrores.get(0));
        } else {
            error = new APIErrorDTO(listaDeErrores);
        }
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<?> handlerRecursoNoEncontradoException(Exception exception, HttpServletRequest request) {
        crearLog("Recurso no encontrado", request, exception);
        APIErrorDTO error = new APIErrorDTO(exception.getMessage());
        return ResponseEntity.status(404).body(error);
    }

    @ExceptionHandler(IntegrantePertenecienteAGrupo.class)
    public ResponseEntity<?> handlerIntegranteYaAsignadoException(Exception exception,HttpServletRequest request){
        crearLog("Integrante ya asignado a un grupo", request, exception);
        APIErrorDTO error=new APIErrorDTO(exception.getMessage());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(CupoExcedidoException.class)
    public ResponseEntity<?> handlerCupoExcedidooException(Exception exception,HttpServletRequest request){
        crearLog("Cupo excedido", request, exception);
        APIErrorDTO error=new APIErrorDTO(exception.getMessage());
        return ResponseEntity.status(400).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlerGenericException(Exception exception, HttpServletRequest request) {

        crearLog("Error no controlado", request, exception);

        APIErrorDTO error = new APIErrorDTO("Error del servidor, vuelva a intentarlo más tarde");

        return ResponseEntity.internalServerError().body(error);
    }

    private void crearLog(String mensaje, HttpServletRequest request, Exception exception) {
        log.error(mensaje + " en {}. Exception: {}", request.getRequestURL(), exception.getLocalizedMessage(), exception);
    }
}
