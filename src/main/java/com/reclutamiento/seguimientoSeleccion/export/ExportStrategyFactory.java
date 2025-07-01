package com.reclutamiento.seguimientoSeleccion.export;

import com.reclutamiento.seguimientoSeleccion.enums.ExportFormat;

/**
 * Fábrica para obtener la estrategia de exportación (Excel o PDF).
 */
public class ExportStrategyFactory {

    /**
     * Devuelve una estrategia de exportación según el formato solicitado.
     *
     * @param format formato de exportación ("excel", "pdf", etc.)
     * @param <T> tipo de datos a exportar
     * @return estrategia concreta para exportar
     */
    @SuppressWarnings("unchecked")
    public static <T> ExportStrategy<T> getStrategy(ExportFormat format) throws IllegalArgumentException {
        return switch (format) {
            case PDF -> (ExportStrategy<T>) new PdfExporter<>();
            case EXCEL -> (ExportStrategy<T>) new ExcelExporter<>();
            case CSV -> (ExportStrategy<T>) new CsvExporter<>();
            default -> throw new IllegalArgumentException("Formato de exportación no soportado: " + format);
        };
    }
}
