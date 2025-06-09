package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa el género declarado por el candidato.
 */
@Getter
public enum Genero {

    /**
     * Masculino.
     */
    MASCULINO("Masculino"),

    /**
     * Femenino.
     */
    FEMENINO("Femenino"),

    /**
     * Otro género no especificado en esta lista.
     */
    OTRO("Otro"),

    /**
     * Prefiere no especificar su género.
     */
    NO_DECLARADO("No declarado");

    private final String label;

    Genero(String label) {
        this.label = label;
    }
}
