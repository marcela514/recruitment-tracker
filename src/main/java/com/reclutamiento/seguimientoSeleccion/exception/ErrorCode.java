package com.reclutamiento.seguimientoSeleccion.exception;

import lombok.Getter;

/**
 * Enum que centraliza y estandariza los códigos de error utilizados en la API REST.
 * <p>
 * Cada constante representa un tipo específico de error y está asociada con un código único,
 * lo cual facilita el rastreo, el registro (logging) y la interpretación de errores en el cliente.
 */
@Getter
public enum ErrorCode {
    /**
     * Error de validación de datos de entrada (por ejemplo, anotaciones {@code @Valid} fallidas).
     */
    VALIDATION_ERROR("VAL_001"),

    /**
     * Violación de restricciones específicas, como valores únicos o reglas de negocio.
     */
    CONSTRAINT_VIOLATION("VAL_002"),

    /**
     * Recurso solicitado no encontrado.
     */
    NOT_FOUND("NOT_FOUND_001"),

    /**
     * Solicitud malformada o con parámetros inválidos.
     */
    BAD_REQUEST("BAD_REQUEST_001"),

    /**
     * Error interno del servidor no previsto.
     */
    INTERNAL_ERROR("INTERNAL_001");

    /**
     * Código único asociado al tipo de error.
     */
    private final String code;

    /**
     * Constructor del enum que asocia un código a cada tipo de error.
     *
     * @param code Código único para el error.
     */
    ErrorCode(String code) {
        this.code = code;
    }
}
