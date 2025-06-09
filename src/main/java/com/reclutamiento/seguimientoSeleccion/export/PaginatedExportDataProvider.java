package com.reclutamiento.seguimientoSeleccion.export;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;

/**
 * ExportDataProvider que obtiene datos paginados a partir de una función que retorna Page<T>.
 *
 * @param <T> Tipo de dato a exportar.
 */
public class PaginatedExportDataProvider<T> implements ExportDataProvider<T> {

    private final Function<Pageable, Page<T>> pageFetcher;
    private final List<String> headers;
    private final List<Function<T, Object>> valueExtractors;
    private int totalCount = -1;

    public PaginatedExportDataProvider(Function<Pageable, Page<T>> pageFetcher,
                                       List<String> headers,
                                       List<Function<T, Object>> valueExtractors) {
        this.pageFetcher = pageFetcher;
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

    /**
     * Obtiene los datos en base a offset y limit haciendo paginación real.
     *
     * @param offset posición inicial (fila).
     * @param limit cantidad máxima de registros.
     * @return lista de datos.
     */
    @Override
    public List<T> getData(int offset, int limit) {
        int pageNum = offset / limit;
        Page<T> page = pageFetcher.apply(Pageable.ofSize(limit).withPage(pageNum));
        if (totalCount == -1) {
            totalCount = (int) page.getTotalElements();
        }
        return page.getContent();
    }

    @Override
    public int getTotalCount() {
        return totalCount;
    }
}
