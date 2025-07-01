package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa los distintos niveles educativos alcanzados por un candidato.
 * Cada valor contiene una clave de mensaje para facilitar la localización (i18n).
 */
@Getter
public enum NivelEducativo implements LocalizableEnum {

    /**
     * Educación primaria.
     */
    PRIMARIA("nivelEducativo.primaria"),

    /**
     * Educación secundaria o bachillerato.
     */
    SECUNDARIA("nivelEducativo.secundaria"),

    /**
     * Estudios técnicos o tecnológicos.
     */
    TECNICO_TECNOLOGO("nivelEducativo.tecnico_tecnologo"),

    /**
     * Educación universitaria de pregrado.
     */
    UNIVERSITARIO("nivelEducativo.universitario"),

    /**
     * Estudios de posgrado, como especialización o maestría.
     */
    POSGRADO("nivelEducativo.posgrado"),

    /**
     * Nivel de doctorado o PhD.
     */
    DOCTORADO("nivelEducativo.doctorado");

    /**
     * Clave de mensaje utilizada para la localización del nivel educativo.
     */
    private final String messageKey;

    /**
     * Constructor del enum que asigna la clave de mensaje.
     *
     * @param messageKey clave utilizada para la localización
     */
    NivelEducativo(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
