package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa los distintos niveles educativos alcanzados por un candidato.
 */
@Getter
public enum NivelEducativo {

    /**
     * Educación primaria.
     */
    PRIMARIA("Primaria"),

    /**
     * Educación secundaria o bachillerato.
     */
    SECUNDARIA("Secundaria"),

    /**
     * Educación técnica o tecnológica.
     */
    TECNICO_TECNOLOGO("Técnico/Tecnólogo"),

    /**
     * Pregrado universitario (Licenciatura).
     */
    UNIVERSITARIO("Universitario"),

    /**
     * Estudios de posgrado (Especialización, Maestría).
     */
    POSGRADO("Posgrado"),

    /**
     * Doctorado o PhD.
     */
    DOCTORADO("Doctorado");

    private final String label;

    NivelEducativo(String label) {
        this.label = label;
    }
}
