package com.reclutamiento.seguimientoSeleccion.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Representa un detalle específico de un error de validación en un campo.
 * <p>
 * Esta clase se utiliza para devolver mensajes de error estructurados
 * cuando se produce una validación fallida en uno o más campos de una solicitud.
 */
@Getter
@Setter
@NoArgsConstructor  // constructor vacío
@AllArgsConstructor // constructor con todos los campos
public class ValidationErrorDetail {

    /**
     * Nombre del campo que produjo el error de validación.
     */
    private String field;

    /**
     * Mensaje descriptivo del error asociado al campo.
     */
    private String message;
}
