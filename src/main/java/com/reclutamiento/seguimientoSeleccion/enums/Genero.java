package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa el género declarado por el candidato.
 * Incluye una clave de mensaje para permitir la localización (i18n).
 */
@Getter
public enum Genero implements LocalizableEnum {

    /**
     * Género masculino.
     */
    MASCULINO("genero.masculino"),

    /**
     * Género femenino.
     */
    FEMENINO("genero.femenino"),

    /**
     * Otro género no listado específicamente.
     */
    OTRO("genero.otro"),

    /**
     * El candidato optó por no declarar su género.
     */
    NO_DECLARADO("genero.no_declarado");

    /**
     * Clave de mensaje utilizada para localización del valor del género.
     */
    private final String messageKey;

    /**
     * Constructor del enum que asigna la clave de mensaje.
     *
     * @param messageKey clave utilizada para la localización del género
     */
    Genero(String messageKey) {
        this.messageKey = messageKey;
    }

    @Override
    public String getMessageKey() {
        return messageKey;
    }
}
