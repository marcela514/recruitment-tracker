package com.reclutamiento.seguimientoSeleccion.exception;

import com.reclutamiento.seguimientoSeleccion.dto.ErrorResponse;
import com.reclutamiento.seguimientoSeleccion.dto.ValidationErrorDetail;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para la aplicación.
 * <p>
 * Intercepta las excepciones lanzadas desde los controladores y
 * genera respuestas consistentes y estructuradas en forma de {@link ErrorResponse}.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación que ocurren cuando se usa {@code @Valid} en los controladores
     * y los datos de entrada no cumplen con las restricciones.
     *
     * @param ex      excepción lanzada por errores de validación de campos
     * @param request contexto de la solicitud web
     * @return respuesta con estado 400 (Bad Request) y detalles de los errores de validación
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        List<ValidationErrorDetail> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ValidationErrorDetail(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse error = ErrorResponse.ofValidationErrors(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                validationErrors,
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.VALIDATION_ERROR.getCode());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja violaciones de restricciones de validación definidas en los beans,
     * como {@code @NotNull}, {@code @Size}, etc., cuando se usan fuera de los controladores.
     *
     * @param ex      excepción lanzada por validaciones de restricciones
     * @param request contexto de la solicitud
     * @return respuesta con estado 400 (Bad Request) y mensajes de error relacionados
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        ErrorResponse error = ErrorResponse.ofMessages(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                messages,
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.CONSTRAINT_VIOLATION.getCode());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones de tipo {@link NotFoundException}, típicamente cuando un recurso no se encuentra.
     *
     * @param ex      excepción personalizada para recursos no encontrados
     * @param request contexto de la solicitud web
     * @return respuesta con estado 404 (Not Found) y mensaje del error
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorResponse error = ErrorResponse.ofMessages(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                List.of(ex.getMessage()),
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.NOT_FOUND.getCode());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja excepciones de tipo {@link IllegalArgumentException},
     * que ocurren generalmente por argumentos inválidos en las solicitudes.
     *
     * @param ex      excepción lanzada por argumentos inválidos
     * @param request contexto de la solicitud
     * @return respuesta con estado 400 (Bad Request) y mensaje del error
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ErrorResponse error = ErrorResponse.ofMessages(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                List.of(ex.getMessage()),
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.BAD_REQUEST.getCode());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones genéricas que extienden de {@link RuntimeException}.
     * Actúa como un catch-all para errores no controlados en tiempo de ejecución.
     *
     * @param ex      excepción lanzada en tiempo de ejecución
     * @param request contexto de la solicitud
     * @return respuesta con estado 500 (Internal Server Error) y mensaje del error
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        ErrorResponse error = ErrorResponse.ofMessages(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                List.of(ex.getMessage()),
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.INTERNAL_ERROR.getCode());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExportLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleExportLimitExceeded(ExportLimitExceededException ex, WebRequest request) {
        ErrorResponse error = ErrorResponse.ofMessages(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Export Limit Exceeded",
                List.of(ex.getMessage()),
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.BAD_REQUEST.getCode());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
