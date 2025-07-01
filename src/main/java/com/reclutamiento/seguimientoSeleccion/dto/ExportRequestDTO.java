package com.reclutamiento.seguimientoSeleccion.dto;

import com.reclutamiento.seguimientoSeleccion.enums.ExportFormat;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO para representar una solicitud de exportación de datos.
 * <p>
 * Permite especificar el formato de exportación y si se desea exportar todos los datos
 * o solo una página específica.
 * </p>
 *
 * <p>Si {@code exportAll} es {@code false}, entonces los campos {@code page} y {@code size} deben ser válidos.</p>
 *
 * @see ExportFormat
 */
@Getter
@Setter
public class ExportRequestDTO {

    /**
     * Formato en el que se desea realizar la exportación (PDF, EXCEL o CSV).
     */
    @NotNull(message = "El formato de exportación es obligatorio.")
    private ExportFormat format;

    /**
     * Indica si se deben exportar todos los registros.
     * Si es {@code true}, se ignorarán los parámetros de paginación.
     */
    private boolean exportAll = false;

    /**
     * Número de la página a exportar (comienza en 0). Solo requerido si {@code exportAll} es {@code false}.
     */
    @Min(value = 0, message = "El número de página debe ser mayor o igual a 0.")
    private int page = 0;

    /**
     * Tamaño de la página (cantidad de registros a exportar). Solo requerido si {@code exportAll} es {@code false}.
     */
    @Min(value = 1, message = "El tamaño de página debe ser al menos 1.")
    private int size = 100;

    /**
     * Validación condicional: si {@code exportAll} es {@code false}, entonces {@code size} debe ser mayor que cero.
     *
     * @return {@code true} si la combinación de valores es válida.
     */
    @AssertTrue(message = "Si no se exportan todos los registros, el tamaño de página debe ser al menos 1.")
    public boolean isValidPagination() {
        return exportAll || size > 0;
    }
}
