package com.reclutamiento.seguimientoSeleccion.logging;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Instant;

/**
 * Interceptor que registra detalles de cada solicitud HTTP entrante y su ejecución.
 * <p>
 * Incluye:
 * <ul>
 *   <li>Método HTTP y URI</li>
 *   <li>IP del cliente</li>
 *   <li>Tiempo de ejecución</li>
 *   <li>Estado de respuesta</li>
 *   <li>Preparado para registrar usuario autenticado (cuando se implemente)</li>
 * </ul>
 */
@Component
public class HttpLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpLoggingInterceptor.class);
    private static final String START_TIME_ATTRIBUTE = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute(START_TIME_ATTRIBUTE, Instant.now().toEpochMilli());

        String method = request.getMethod();
        String uri = request.getRequestURI();
        String ip = request.getRemoteAddr();

        // Placeholder para autenticación futura
        // String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // logger.info("➡️  [{} {}] por usuario: {}, IP: {}", method, uri, username, ip);

        logger.info("➡️  [{} {}] desde IP: {}", method, uri, ip);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        long startTime = (long) request.getAttribute(START_TIME_ATTRIBUTE);
        long duration = Instant.now().toEpochMilli() - startTime;

        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();

        logger.info("⬅️  [{} {}] finalizado con status {} en {} ms", method, uri, status, duration);

        if (ex != null) {
            logger.error("❌ Excepción en [{} {}]: {}", method, uri, ex.getMessage(), ex);
        }
    }
}
