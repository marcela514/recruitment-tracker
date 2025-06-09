package com.reclutamiento.seguimientoSeleccion.dto;

/**
 * DTO que encapsula el resultado de una operación de exportación.
 * Contiene los datos binarios exportados, el nombre del archivo y el tipo de contenido (MIME).
 */
public record ExportResult(byte[] data, String filename, String contentType) {
}
