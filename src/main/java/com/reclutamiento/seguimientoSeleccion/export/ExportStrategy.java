package com.reclutamiento.seguimientoSeleccion.export;

public interface ExportStrategy<T> {
    byte[] export(ExportDataProvider<T> provider) throws Exception;
    String getFileExtension();
    String getContentType();
}
