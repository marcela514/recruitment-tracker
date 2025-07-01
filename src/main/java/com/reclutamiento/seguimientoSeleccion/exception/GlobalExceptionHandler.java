package com.reclutamiento.seguimientoSeleccion.exception;

import com.reclutamiento.seguimientoSeleccion.dto.ErrorResponse;
import com.reclutamiento.seguimientoSeleccion.dto.ValidationErrorDetail;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Manejador global de excepciones para la API.
 * <p>
 * Esta clase centraliza el tratamiento de errores comunes lanzados durante el procesamiento de peticiones HTTP.
 * Se encarga de construir respuestas consistentes con mensajes internacionalizados y estructuras uniformes.
 */
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    /**
     * Obtiene un mensaje internacionalizado para un {@link ErrorCode} dado.
     *
     * @param errorCode Código de error.
     * @param locale    Localización actual.
     * @return Mensaje localizado o clave si no se encuentra traducción.
     */
    private String getLocalizedMessage(ErrorCode errorCode, Locale locale) {
        return messageSource.getMessage(errorCode.getMessageKey(), null, locale);
    }

    /**
     * Maneja errores de validación lanzados por anotaciones {@code @Valid} al usar {@link MethodArgumentNotValidException}.
     *
     * @param ex      Excepción capturada.
     * @param request Detalles de la solicitud.
     * @return Respuesta con detalles de validación.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        Locale locale = request.getLocale();
        List<ValidationErrorDetail> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> new ValidationErrorDetail(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponse error = ErrorResponse.fromValidationErrors(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                getLocalizedMessage(ErrorCode.VALIDATION_ERROR, locale),
                validationErrors,
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.VALIDATION_ERROR.getCode());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores de violación de restricciones como {@link jakarta.validation.constraints.NotNull} u otras validaciones a nivel de clase.
     *
     * @param ex      Excepción capturada.
     * @param request Detalles de la solicitud.
     * @return Respuesta con mensajes de error específicos.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        Locale locale = request.getLocale();
        List<String> messages = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        ErrorResponse error = ErrorResponse.fromMessages(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                getLocalizedMessage(ErrorCode.CONSTRAINT_VIOLATION, locale),
                messages,
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.CONSTRAINT_VIOLATION.getCode());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores cuando no se encuentra un recurso específico.
     *
     * @param ex      Excepción {@link NotFoundException} personalizada.
     * @param request Detalles de la solicitud.
     * @return Respuesta con mensaje de error 404.
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex, WebRequest request) {
        Locale locale = request.getLocale();
        ErrorResponse error = ErrorResponse.fromMessages(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                getLocalizedMessage(ErrorCode.NOT_FOUND, locale),
                List.of(ex.getMessage()),
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.NOT_FOUND.getCode());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja errores provocados por argumentos ilegales, como estados inválidos o entradas fuera de rango.
     *
     * @param ex      Excepción {@link IllegalArgumentException}.
     * @param request Detalles de la solicitud.
     * @return Respuesta con error 400.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        Locale locale = request.getLocale();
        ErrorResponse error = ErrorResponse.fromMessages(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                getLocalizedMessage(ErrorCode.BAD_REQUEST, locale),
                List.of(ex.getMessage()),
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.BAD_REQUEST.getCode());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja excepciones no controladas a nivel de aplicación.
     *
     * @param ex      Cualquier excepción de tipo {@link RuntimeException}.
     * @param request Detalles de la solicitud.
     * @return Respuesta genérica con error 500.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Locale locale = request.getLocale();
        ErrorResponse error = ErrorResponse.fromMessages(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getLocalizedMessage(ErrorCode.INTERNAL_ERROR, locale),
                List.of(ex.getMessage()),
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.INTERNAL_ERROR.getCode());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Maneja las excepciones de tipo {@link ExportLimitExceededException} que ocurren cuando un usuario
     * intenta exportar más elementos de los permitidos en un formato específico.
     * <p>
     * Este método construye una respuesta detallada con un mensaje localizado, incluyendo el número de elementos
     * solicitados, el formato de exportación y el límite máximo permitido, para proporcionar retroalimentación clara
     * al cliente.
     *
     * @param ex      la excepción lanzada que contiene detalles del límite excedido.
     * @param request la solicitud web actual, usada para obtener la localización (locale) del cliente.
     * @return una {@link ResponseEntity} con un objeto {@link ErrorResponse} que describe el error,
     *         con código de estado {@code 400 Bad Request}.
     */
    @ExceptionHandler(ExportLimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleExportLimitExceeded(ExportLimitExceededException ex, WebRequest request) {
        Locale locale = request.getLocale();

        String title = messageSource.getMessage(
                ErrorCode.EXPORT_LIMIT_EXCEEDED.getMessageKey() + ".title", null, locale);

        String detailMessage = messageSource.getMessage(
                ErrorCode.EXPORT_LIMIT_EXCEEDED.getMessageKey() + ".detail",
                new Object[]{ex.getRequested(), ex.getFormat(), ex.getMaxAllowed()},
                locale);


        ErrorResponse error = ErrorResponse.fromMessages(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                title,
                List.of(detailMessage),
                request.getDescription(false).replace("uri=", "")
        );
        error.setErrorCode(ErrorCode.EXPORT_LIMIT_EXCEEDED.getCode());


        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
