package com.reclutamiento.seguimientoSeleccion.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada lanzada cuando se excede el límite permitido
 * para la exportación de datos.
 */
@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExportLimitExceededException extends RuntimeException {

    private final String format;
    private final int maxAllowed;
    private final int requested;

    public ExportLimitExceededException(String format, int maxAllowed, int requested) {
        super(String.format("La cantidad de filas (%d) excede el límite máximo permitido para exportación en formato '%s': %d", requested, format, maxAllowed));
        this.format = format;
        this.maxAllowed = maxAllowed;
        this.requested = requested;
    }
}
