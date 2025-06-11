package com.reclutamiento.seguimientoSeleccion.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;

/**
 * Configuración para habilitar y personalizar la ejecución asíncrona en la aplicación.
 * <p>
 * Esta clase define un {@link ThreadPoolTaskExecutor} como ejecutor de tareas
 * asíncronas y proporciona un manejador para excepciones no capturadas en métodos @Async.
 * </p>
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

    /**
     * Define el bean {@code taskExecutor} utilizado por Spring para ejecutar métodos anotados con {@code @Async}.
     *
     * @return un {@link Executor} basado en {@link ThreadPoolTaskExecutor} con parámetros configurados.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); // Número mínimo de hilos activos
        executor.setMaxPoolSize(10); // Número máximo de hilos permitidos
        executor.setQueueCapacity(100); // Capacidad de la cola de tareas antes de crear nuevos hilos
        executor.setThreadNamePrefix("Async-"); // Prefijo de nombre para los hilos
        executor.initialize();
        return executor;
    }

    /**
     * Define el manejador global para excepciones no capturadas en métodos asíncronos.
     *
     * @return un {@link AsyncUncaughtExceptionHandler} que registra la excepción.
     */
    @Bean
    public AsyncUncaughtExceptionHandler asyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            /**
             * Maneja excepciones no capturadas lanzadas por métodos asíncronos.
             *
             * @param throwable excepción lanzada
             * @param method    método donde ocurrió la excepción
             * @param params    parámetros del método
             */
            @Override
            public void handleUncaughtException(
                    @NonNull Throwable throwable,
                    @NonNull Method method,
                    @NonNull Object... params) {
                logger.error("Excepción no capturada en método async '{}': {}", method.getName(), throwable.getMessage(), throwable);
            }
        };
    }
}
