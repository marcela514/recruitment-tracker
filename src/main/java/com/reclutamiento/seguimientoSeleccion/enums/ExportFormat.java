package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa los formatos disponibles para la exportación de datos.
 * Cada formato incluye una clave de mensaje asociada para su localización (i18n).
 */
@Getter
public enum ExportFormat implements LocalizableEnum {

    /**
     * Formato PDF (Portable Document Format).
     */
    PDF("exportFormat.pdf"),

    /**
     * Formato Excel (archivo de hoja de cálculo).
     */
    EXCEL("exportFormat.excel"),

    /**
     * Formato CSV (Comma-Separated Values).
     */
    CSV("exportFormat.csv");

    /**
     * Clave de mensaje usada para la localización del formato.
     */
    private final String messageKey;

    /**
     * Constructor del enum que asigna la clave de mensaje.
     *
     * @param messageKey clave utilizada para la localización del formato
     */
    ExportFormat(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
