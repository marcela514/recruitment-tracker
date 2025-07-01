package com.reclutamiento.seguimientoSeleccion.exception;

import com.reclutamiento.seguimientoSeleccion.enums.ExportFormat;
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

    private final ExportFormat format;
    private final int maxAllowed;
    private final int requested;

    /**
     * Constructor sin mensaje concreto, para permitir internacionalización desde el handler.
     */
    public ExportLimitExceededException(ExportFormat format, int maxAllowed, int requested) {
        // Mensaje técnico solo para fines de desarrollo/log
        super("Export limit exceeded");
        this.format = format;
        this.maxAllowed = maxAllowed;
        this.requested = requested;
    }
}
