package com.reclutamiento.seguimientoSeleccion;

import com.reclutamiento.seguimientoSeleccion.config.ExportLimitsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Clase principal de la aplicación Spring Boot para el módulo de seguimiento de selección.
 * <p>
 * Esta clase arranca la aplicación y habilita la carga de propiedades personalizadas
 * definidas en {@link ExportLimitsProperties}.
 * </p>
 */
@SpringBootApplication
@EnableConfigurationProperties(ExportLimitsProperties.class)
public class SeguimientoSeleccionApplication {

	/**
	 * Punto de entrada principal de la aplicación.
	 *
	 * @param args argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SeguimientoSeleccionApplication.class, args);
	}

}
