package com.reclutamiento.seguimientoSeleccion;

import com.reclutamiento.seguimientoSeleccion.config.ExportLimitsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Clase principal de la aplicación Spring Boot para el módulo de seguimiento de procesos de selección.
 * <p>
 * Esta clase se encarga de iniciar la aplicación mediante {@link SpringApplication#run(Class, String...)}.
 * Además, habilita:
 * <ul>
 *     <li>La carga de propiedades externas mediante {@link ExportLimitsProperties}.</li>
 *     <li>La auditoría de entidades JPA con {@link EnableJpaAuditing}, usando un proveedor de auditor personalizado.</li>
 * </ul>
 * </p>
 *
 * @see com.reclutamiento.seguimientoSeleccion.model.audit.Auditable
 * @see com.reclutamiento.seguimientoSeleccion.config.AuditConfig
 */
@SpringBootApplication
@EnableConfigurationProperties(ExportLimitsProperties.class)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SeguimientoSeleccionApplication {

	private static final Logger logger = LoggerFactory.getLogger(SeguimientoSeleccionApplication.class);

	/**
	 * Punto de entrada principal de la aplicación.
	 * Lanza la aplicación Spring Boot.
	 *
	 * @param args argumentos de línea de comandos.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SeguimientoSeleccionApplication.class, args);
		logger.info("🚀 Aplicación SeguimientoSeleccion iniciada correctamente.");
	}
}
