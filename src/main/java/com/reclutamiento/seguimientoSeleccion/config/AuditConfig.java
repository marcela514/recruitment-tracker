package com.reclutamiento.seguimientoSeleccion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Configuración de auditoría para Spring Data JPA.
 * <p>
 * Esta clase define un {@link AuditorAware} que proporciona el nombre del usuario responsable
 * de crear o modificar las entidades. En este caso, se utiliza un valor fijo ("sistema"),
 * pero puede ser reemplazado por una lógica que obtenga el usuario autenticado.
 * </p>
 *
 * <p>Esta configuración es necesaria para habilitar la auditoría automática con anotaciones
 * como {@code @CreatedBy} y {@code @LastModifiedBy} en las entidades.</p>
 *
 * @see com.reclutamiento.seguimientoSeleccion.model.audit.Auditable
 */
@Configuration
public class AuditConfig {

    /**
     * Proveedor de auditor responsable de devolver el nombre del usuario actual.
     * <p>
     * Actualmente retorna un valor constante "sistema", pero puede ser extendido
     * para devolver el usuario autenticado, por ejemplo, desde el contexto de seguridad.
     * </p>
     *
     * @return una instancia de {@link AuditorAware} con el auditor actual
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("sistema");
    }
}
