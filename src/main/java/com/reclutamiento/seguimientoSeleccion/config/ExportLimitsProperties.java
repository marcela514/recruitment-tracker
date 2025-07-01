package com.reclutamiento.seguimientoSeleccion.config;

import com.reclutamiento.seguimientoSeleccion.enums.ExportFormat;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Clase de configuración que define los límites máximos de registros
 * permitidos para exportaciones en distintos formatos (PDF, Excel, CSV), así como
 * el tiempo de expiración de una exportación en memoria.
 *
 * <p>
 * Estos valores se leen desde el archivo {@code application.properties} o {@code application.yml}
 * utilizando el prefijo {@code export.limits}.
 * </p>
 *
 * <p>Ejemplo en {@code application.properties}:</p>
 *
 * <pre>{@code
 * export.limits.pdf=2000
 * export.limits.excel=50000
 * export.limits.csv=100000
 * export.limits.expirationMinutes=5
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
     * Tiempo (en minutos) durante el cual una exportación estará disponible en memoria
     * antes de ser eliminada automáticamente. Valor por defecto: 5 minutos.
     */
    @Min(1)
    private int expirationMinutes = 5;

    /**
     * Retorna el límite de registros permitido según el formato de exportación.
     *
     * @param format formato de exportación: "pdf", "excel" o "csv"
     * @return el límite configurado para el formato especificado
     * @throws IllegalArgumentException si el formato no está soportado
     */
    public int getLimitByFormat(ExportFormat format) {
        return switch (format) {
            case PDF -> pdf;
            case EXCEL -> excel;
            case CSV -> csv;
        };
    }

}
