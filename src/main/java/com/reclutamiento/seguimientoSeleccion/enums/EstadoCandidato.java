package com.reclutamiento.seguimientoSeleccion.enums;

import lombok.Getter;

/**
 * Enum que representa el estado actual del candidato dentro del sistema.
 */
@Getter
public enum EstadoCandidato {

    /**
     * Candidato activo que puede postularse a ofertas.
     */
    ACTIVO("Activo"),

    /**
     * Candidato inactivo, posiblemente por eliminación lógica o baja voluntaria.
     */
    INACTIVO("Inactivo"),

    /**
     * Candidato bloqueado por motivos administrativos o disciplinarios.
     */
    BLOQUEADO("Bloqueado");

    private final String label;

    EstadoCandidato(String label) {
        this.label = label;
    }
}
