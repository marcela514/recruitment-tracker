package com.reclutamiento.seguimientoSeleccion.export;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Exportador de datos a CSV simple.
 * Usa comas como separador, pone comillas dobles para valores con comas o comillas.
 *
 * @param <T> Tipo de dato a exportar.
 */
public class CsvExporter<T> implements ExportStrategy<T> {

    private static final int PAGE_SIZE = 500;

    @Override
    public byte[] export(ExportDataProvider<T> provider) throws Exception {
        StringBuilder sb = new StringBuilder();

        // Agregar encabezados
        List<String> headers = provider.getHeaders();
        sb.append(String.join(",", headers)).append("\n");

        int offset = 0;
        int total = provider.getTotalCount();
        List<Function<T, Object>> extractors = provider.getValueExtractors();

        while (offset < total) {
            List<T> data = provider.getData(offset, Math.min(PAGE_SIZE, total - offset));

            for (T item : data) {
                List<String> values = new ArrayList<>();
                for (Function<T, Object> extractor : extractors) {
                    Object val = extractor.apply(item);
                    values.add(escapeCsv(val == null ? "" : val.toString()));
                }
                sb.append(String.join(",", values)).append("\n");
            }

            offset += PAGE_SIZE;
        }

        return sb.toString().getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Escapa comillas dobles y pone el valor entre comillas si contiene comas o comillas.
     */
    private String escapeCsv(String value) {
        if (value.contains("\"")) {
            value = value.replace("\"", "\"\"");
        }
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = "\"" + value + "\"";
        }
        return value;
    }

    @Override
    public String getFileExtension() {
        return "csv";
    }

    @Override
    public String getContentType() {
        return "text/csv";
    }
}
