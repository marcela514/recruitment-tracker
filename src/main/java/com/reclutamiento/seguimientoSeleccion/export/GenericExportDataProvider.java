package com.reclutamiento.seguimientoSeleccion.export;

import java.util.List;
import java.util.function.Function;

/**
 * Proveedor genérico para exportación de datos con paginación.
 *
 * @param <T> Tipo de objeto a exportar.
 */
public class GenericExportDataProvider<T> implements ExportDataProvider<T> {

    private final List<T> dataList;
    private final List<String> headers;
    private final List<Function<T, Object>> valueExtractors;

    /**
     * Constructor que recibe la lista de datos, encabezados y extractores.
     *
     * @param dataList lista completa de objetos a exportar
     * @param headers lista con nombres de columnas
     * @param valueExtractors funciones para extraer valores de cada columna
     */
    public GenericExportDataProvider(List<T> dataList, List<String> headers, List<Function<T, Object>> valueExtractors) {
        this.dataList = dataList;
        this.headers = headers;
        this.valueExtractors = valueExtractors;
    }

    @Override
    public List<String> getHeaders() {
        return headers;
    }

    @Override
    public List<Function<T, Object>> getValueExtractors() {
        return valueExtractors;
    }

    @Override
    public List<T> getData(int offset, int limit) {
        if (offset >= dataList.size()) {
            return List.of();
        }
        int toIndex = Math.min(offset + limit, dataList.size());
        return dataList.subList(offset, toIndex);
    }

    @Override
    public int getTotalCount() {
        return dataList.size();
    }
}
