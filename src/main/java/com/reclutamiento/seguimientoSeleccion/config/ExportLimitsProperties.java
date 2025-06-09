package com.reclutamiento.seguimientoSeleccion.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "export.limits")
public class ExportLimitsProperties {

    private int pdf = 2000;
    private int excel = 50000;
    private int csv = 100000;

    public int getLimitByFormat(String format) {
        return switch (format.toLowerCase()) {
            case "pdf" -> pdf;
            case "excel" -> excel;
            case "csv" -> csv;
            default -> throw new IllegalArgumentException("Formato no soportado: " + format);
        };
    }
}
