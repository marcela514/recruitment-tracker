package com.reclutamiento.seguimientoSeleccion.dto;

/**
 * Representa un detalle específico de un error de validación en un campo.
 * <p>
 * Esta clase se utiliza para devolver mensajes de error estructurados
 * cuando se produce una validación fallida en uno o más campos de una solicitud.
 */
public record ValidationErrorDetail(
        String field,
        String message
) {}
