package com.reclutamiento.seguimientoSeleccion.export;

import java.util.List;
import java.util.function.Function;

/**
 * Implementación de {@link ExportDataProvider} que utiliza una lista completa de datos en memoria.
 * <p>
 * Este proveedor es ideal para exportaciones que no requieren paginación, ya que todos los registros
 * están disponibles desde el inicio.
 * </p>
 *
 * @param <T> tipo de los objetos que se exportarán
 */
public class ListExportDataProvider<T> implements ExportDataProvider<T> {

    private final List<T> data;
    private final List<String> headers;
    private final List<Function<T, Object>> valueExtractors;

    /**
     * Crea una instancia del proveedor de datos basado en una lista completa.
     *
     * @param data            lista completa de datos a exportar
     * @param headers         lista de encabezados de columna
     * @param valueExtractors funciones para extraer los valores de cada columna desde un objeto
     */
    public ListExportDataProvider(
            List<T> data,
            List<String> headers,
            List<Function<T, Object>> valueExtractors
    ) {
        this.data = data;
        this.headers = headers;
        this.valueExtractors = valueExtractors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getHeaders() {
        return headers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Function<T, Object>> getValueExtractors() {
        return valueExtractors;
    }

    /**
     * Retorna una sublista de los datos entre el índice {@code offset} (inclusive)
     * y {@code offset + limit} (exclusive), manejando límites seguros.
     *
     * @param offset posición inicial de los datos
     * @param limit  cantidad máxima de elementos a retornar
     * @return sublista de datos correspondiente
     */
    @Override
    public List<T> getData(int offset, int limit) {
        int fromIndex = Math.min(offset, data.size());
        int toIndex = Math.min(offset + limit, data.size());
        return data.subList(fromIndex, toIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTotalCount() {
        return data.size();
    }
}
