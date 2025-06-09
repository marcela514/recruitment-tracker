package com.reclutamiento.seguimientoSeleccion.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que representa la estructura de una respuesta de error para el seguimiento de selección.
 */
@Getter
@Setter
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private List<String> messages;
    private List<ValidationErrorDetail> validationErrors;
    private String path;
    private String errorCode;

    public ErrorResponse() {}

    private ErrorResponse(LocalDateTime timestamp, int status, String error,
                          List<String> messages, List<ValidationErrorDetail> validationErrors,
                          String path, String errorCode) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.messages = messages;
        this.validationErrors = validationErrors;
        this.path = path;
        this.errorCode = errorCode;
    }

    public static ErrorResponse ofMessages(LocalDateTime timestamp, int status, String error,
                                           List<String> messages, String path) {
        return new ErrorResponse(timestamp, status, error, messages, null, path, null);
    }

    public static ErrorResponse ofValidationErrors(LocalDateTime timestamp, int status, String error,
                                                   List<ValidationErrorDetail> validationErrors, String path) {
        return new ErrorResponse(timestamp, status, error, null, validationErrors, path, null);
    }
}
