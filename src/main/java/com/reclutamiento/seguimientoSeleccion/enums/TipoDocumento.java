package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa los tipos de documento de identidad que un candidato puede poseer.
 * Cada valor incluye una clave de mensaje que facilita la internacionalización (i18n).
 */
@Getter
public enum TipoDocumento implements LocalizableEnum {

    /**
     * Cédula de ciudadanía (típico de Colombia).
     */
    CC("tipoDocumento.cc"),

    /**
     * Cédula de extranjería.
     */
    CE("tipoDocumento.ce"),

    /**
     * Pasaporte.
     */
    PASAPORTE("tipoDocumento.pasaporte"),

    /**
     * Documento Nacional de Identidad (usado en varios países de habla hispana).
     */
    DNI("tipoDocumento.dni"),

    /**
     * Licencia de conducción.
     */
    LICENCIA_CONDUCCION("tipoDocumento.licencia_conduccion"),

    /**
     * Otro tipo de documento no especificado en las opciones anteriores.
     */
    OTRO("tipoDocumento.otro");

    /**
     * Clave de mensaje para localización.
     */
    private final String messageKey;

    /**
     * Constructor que asigna la clave de mensaje al tipo de documento.
     *
     * @param messageKey Clave de internacionalización asociada al tipo de documento.
     */
    TipoDocumento(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
