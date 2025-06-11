package com.reclutamiento.seguimientoSeleccion.exception;

import lombok.Getter;

/**
 * Enum que centraliza y estandariza los códigos de error utilizados en la API REST.
 * <p>
 * Cada constante representa un tipo específico de error, asociado con:
 * <ul>
 *     <li>Un código único identificador del error (para trazabilidad técnica).</li>
 *     <li>Una clave de mensaje internacionalizable (para mostrar mensajes amigables al usuario).</li>
 * </ul>
 * Estos errores se pueden usar en excepciones controladas y respuestas personalizadas.
 */
@Getter
public enum ErrorCode {

    /**
     * Error de validación de datos de entrada (por ejemplo, anotaciones {@code @Valid} fallidas).
     */
    VALIDATION_ERROR("VAL_001", "error.validation"),

    /**
     * Violación de restricciones específicas, como valores únicos o reglas de negocio.
     */
    CONSTRAINT_VIOLATION("VAL_002", "error.constraint_violation"),

    /**
     * Recurso solicitado no encontrado.
     */
    NOT_FOUND("NOT_FOUND_001", "error.not_found"),

    /**
     * Solicitud malformada o con parámetros inválidos.
     */
    BAD_REQUEST("BAD_REQUEST_001", "error.bad_request"),

    /**
     * Error interno del servidor no previsto.
     */
    INTERNAL_ERROR("INTERNAL_001", "error.internal"),

    /**
     * Se excedió el límite permitido para la exportación.
     */
    EXPORT_LIMIT_EXCEEDED("EXPORT_001", "error.export_limit_exceeded");

    private final String code;
    private final String messageKey;

    ErrorCode(String code, String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }
}
