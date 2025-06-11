package com.reclutamiento.seguimientoSeleccion.config;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Clase de configuración que define los límites máximos de registros
 * permitidos para exportaciones en distintos formatos (PDF, Excel, CSV).
 * <p>
 * Estos valores se leen desde el archivo {@code application.properties}
 * usando el prefijo {@code export.limits}. Ejemplo:
 * </p>
 *
 * <pre>{@code
 * export.limits.pdf=2000
 * export.limits.excel=50000
 * export.limits.csv=100000
 * }</pre>
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "export.limits")
public class ExportLimitsProperties {

    /**
     * Límite máximo de registros para exportación en formato PDF.
     * Valor por defecto: 2000.
     */
    @Min(1)
    private int pdf = 2000;

    /**
     * Límite máximo de registros para exportación en formato Excel.
     * Valor por defecto: 50000.
     */
    @Min(1)
    private int excel = 50000;

    /**
     * Límite máximo de registros para exportación en formato CSV.
     * Valor por defecto: 100000.
     */
    @Min(1)
    private int csv = 100000;

    /**
     * Retorna el límite de registros permitido según el formato indicado.
     *
     * @param format Formato de exportación: "pdf", "excel" o "csv".
     * @return Límite configurado para el formato.
     * @throws IllegalArgumentException Si el formato no está soportado.
     */
    public int getLimitByFormat(String format) {
        return switch (format.toLowerCase()) {
            case "pdf" -> pdf;
            case "excel" -> excel;
            case "csv" -> csv;
            default -> throw new IllegalArgumentException("Formato no soportado: " + format);
        };
    }
}
