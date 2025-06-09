package com.reclutamiento.seguimientoSeleccion.export;

import com.reclutamiento.seguimientoSeleccion.export.ExportDataProvider;

/**
 * Estrategia de exportación para archivos PDF.
 * Utiliza la clase PdfExporter para generar el archivo,
 * implementando la interfaz ExportStrategy.
 *
 * @param <T> Tipo de dato que se exporta.
 */
public class PdfExportStrategy<T> implements ExportStrategy<T> {

    private final PdfExporter<T> exporter = new PdfExporter<>();

    /**
     * Exporta los datos proporcionados por el ExportDataProvider a un archivo PDF
     * en formato byte array.
     *
     * @param provider Proveedor de datos para exportar.
     * @return arreglo de bytes con el archivo PDF generado.
     * @throws Exception en caso de error durante la exportación.
     */
    @Override
    public byte[] export(ExportDataProvider<T> provider) throws Exception {
        return exporter.export(provider);
    }

    /**
     * Obtiene la extensión del archivo generado.
     *
     * @return extensión de archivo, por ejemplo "pdf".
     */
    @Override
    public String getFileExtension() {
        return "pdf";
    }

    /**
     * Obtiene el content type para el archivo PDF.
     *
     * @return MIME type para PDF.
     */
    @Override
    public String getContentType() {
        return "application/pdf";
    }
}
