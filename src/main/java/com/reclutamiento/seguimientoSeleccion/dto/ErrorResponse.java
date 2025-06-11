package com.reclutamiento.seguimientoSeleccion.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que representa la estructura estandarizada de una respuesta de error en la aplicación
 * de seguimiento de selección. Esta clase permite detallar tanto errores generales como errores
 * de validación específicos de los campos.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    /**
     * Marca de tiempo en la que ocurrió el error.
     * Formato: yyyy-MM-dd'T'HH:mm:ss
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * Código de estado HTTP correspondiente al error.
     */
    private int status;

    /**
     * Descripción corta del tipo de error (por ejemplo: "Bad Request", "Internal Server Error").
     */
    private String error;

    /**
     * Lista de mensajes de error generales relacionados con la solicitud.
     * No se incluye si hay errores de validación específicos.
     */
    private List<String> messages;

    /**
     * Lista detallada de errores de validación por campo.
     * Solo se incluye si existen errores de validación.
     */
    private List<ValidationErrorDetail> validationErrors;

    /**
     * Ruta del endpoint donde ocurrió el error.
     */
    private String path;

    /**
     * Código de error personalizado (opcional) que puede ser útil para clientes o trazabilidad interna.
     */
    private String errorCode;

    /**
     * Método de fábrica para crear una respuesta de error general (sin errores de validación).
     *
     * @param timestamp fecha y hora del error
     * @param status    código de estado HTTP
     * @param error     descripción del error
     * @param messages  lista de mensajes de error
     * @param path      ruta de la solicitud
     * @return instancia de {@link ErrorResponse}
     */
    public static ErrorResponse fromMessages(LocalDateTime timestamp, int status, String error,
                                             List<String> messages, String path) {
        return new ErrorResponse(timestamp, status, error, messages, null, path, null);
    }

    /**
     * Método de fábrica para crear una respuesta de error con errores de validación por campo.
     *
     * @param timestamp        fecha y hora del error
     * @param status           código de estado HTTP
     * @param error            descripción del error
     * @param validationErrors lista de errores de validación por campo
     * @param path             ruta de la solicitud
     * @return instancia de {@link ErrorResponse}
     */
    public static ErrorResponse fromValidationErrors(LocalDateTime timestamp, int status, String error,
                                                     List<ValidationErrorDetail> validationErrors, String path) {
        return new ErrorResponse(timestamp, status, error, null, validationErrors, path, null);
    }
}
