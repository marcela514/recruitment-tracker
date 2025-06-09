package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa los tipos de documento de identidad que un candidato puede poseer.
 */
@Getter
public enum TipoDocumento {

    /**
     * Cédula de ciudadanía (Colombia).
     */
    CC("Cédula de ciudadanía"),

    /**
     * Cédula de extranjería.
     */
    CE("Cédula de extranjería"),

    /**
     * Pasaporte.
     */
    PASAPORTE("Pasaporte"),

    /**
     * Documento Nacional de Identidad.
     */
    DNI("Documento Nacional de Identidad"),

    /**
     * Licencia de conducción (en algunos países se acepta como documento válido).
     */
    LICENCIA_CONDUCCION("Licencia de conducción"),

    /**
     * Otro tipo de documento no especificado.
     */
    OTRO("Otro");

    private final String label;

    TipoDocumento(String label) {
        this.label = label;
    }
}
