package com.reclutamiento.seguimientoSeleccion.config;

import com.reclutamiento.seguimientoSeleccion.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global de interceptores para Spring MVC.
 * Registra {@link HttpLoggingInterceptor} para capturar logs HTTP entrantes.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final HttpLoggingInterceptor httpLoggingInterceptor;

    @Autowired
    public WebConfig(HttpLoggingInterceptor httpLoggingInterceptor) {
        this.httpLoggingInterceptor = httpLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpLoggingInterceptor);
    }
}
