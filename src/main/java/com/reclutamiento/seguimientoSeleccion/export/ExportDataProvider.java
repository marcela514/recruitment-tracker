package com.reclutamiento.seguimientoSeleccion.export;

import java.util.List;
import java.util.function.Function;

/**
 * Interfaz que provee los datos necesarios para exportar listas de objetos
 * en formatos como Excel o PDF, de manera genérica y sin usar reflexión.
 * Se ha extendido para soportar paginación y streaming de datos,
 * permitiendo obtener bloques o páginas parciales de datos para exportar.
 *
 * @param <T> Tipo de los objetos que se exportarán.
 */
public interface ExportDataProvider<T> {

    /**
     * Obtiene la lista de encabezados (nombres de columnas) que se usarán
     * en la exportación. Deben corresponder en orden con los extractores.
     *
     * @return Lista de nombres de columnas para la cabecera.
     */
    List<String> getHeaders();

    /**
     * Obtiene la lista de funciones que extraen valores de cada objeto
     * para cada columna a exportar. El orden debe coincidir con los encabezados.
     *
     * @return Lista de funciones que extraen el valor de cada columna.
     */
    List<Function<T, Object>> getValueExtractors();

    /**
     * Obtiene un bloque o página de datos para exportar.
     * Este método permite implementar la exportación en streaming
     * o paginada, evitando cargar toda la lista en memoria.
     *
     * @param offset índice del primer elemento a obtener (base 0).
     * @param limit  número máximo de elementos a obtener.
     * @return lista parcial de datos para exportar.
     */
    List<T> getData(int offset, int limit);

    /**
     * Obtiene el total de elementos disponibles para exportar.
     * Esto es útil para iterar y exportar datos por bloques.
     *
     * @return total de elementos disponibles.
     */
    int getTotalCount();
}
