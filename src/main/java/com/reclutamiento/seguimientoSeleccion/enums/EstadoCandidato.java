package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa el estado actual del candidato dentro del sistema.
 * Cada estado incluye una clave de mensaje asociada para su localización (i18n).
 */
@Getter
public enum EstadoCandidato {

    /**
     * El candidato está activo en el proceso de selección.
     */
    ACTIVO("estadoCandidato.activo"),

    /**
     * El candidato está inactivo y no participa actualmente en procesos de selección.
     */
    INACTIVO("estadoCandidato.inactivo"),

    /**
     * El candidato ha sido bloqueado del sistema por alguna razón administrativa.
     */
    BLOQUEADO("estadoCandidato.bloqueado");

    /**
     * Clave de mensaje usada para la localización del estado.
     */
    private final String messageKey;

    /**
     * Constructor del enum que asigna la clave de mensaje.
     *
     * @param messageKey clave utilizada para la localización del estado
     */
    EstadoCandidato(String messageKey) {
        this.messageKey = messageKey;
    }
}
