package com.reclutamiento.seguimientoSeleccion.export;

import com.reclutamiento.seguimientoSeleccion.export.ExportDataProvider;

/**
 * Estrategia de exportación para archivos Excel (.xlsx).
 * Utiliza la clase ExcelExporter para generar el archivo,
 * implementando la interfaz ExportStrategy.
 *
 * @param <T> Tipo de dato que se exporta.
 */
public class ExcelExportStrategy<T> implements ExportStrategy<T> {

    private final ExcelExporter<T> exporter = new ExcelExporter<>();

    /**
     * Exporta los datos proporcionados por el ExportDataProvider a un archivo Excel
     * en formato byte array.
     *
     * @param provider Proveedor de datos para exportar.
     * @return arreglo de bytes con el archivo Excel generado.
     * @throws Exception en caso de error durante la exportación.
     */
    @Override
    public byte[] export(ExportDataProvider<T> provider) throws Exception {
        return exporter.export(provider);
    }

    /**
     * Obtiene la extensión del archivo generado.
     *
     * @return extensión de archivo, por ejemplo "xlsx".
     */
    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    /**
     * Obtiene el content type para el archivo Excel.
     *
     * @return MIME type para Excel.
     */
    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }
}
