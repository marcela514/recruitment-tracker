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

    /**
     * Constructor sin mensaje concreto, para permitir internacionalización desde el handler.
     */
    public ExportLimitExceededException(String format, int maxAllowed, int requested) {
        // Mensaje técnico solo para fines de desarrollo/log
        super("Export limit exceeded");
        this.format = format;
        this.maxAllowed = maxAllowed;
        this.requested = requested;
    }
}
