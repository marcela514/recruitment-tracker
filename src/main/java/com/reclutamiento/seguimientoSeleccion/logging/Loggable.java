package com.reclutamiento.seguimientoSeleccion.logging;

import java.lang.annotation.*;

/**
 * Anotación personalizada para habilitar el registro (logging) automático
 * de la ejecución de métodos o clases mediante Aspect-Oriented Programming (AOP).
 * <p>
 * Puede aplicarse a nivel de clase o método. Cuando se usa, un aspecto (aspect)
 * interceptará la ejecución y registrará información útil como:
 * <ul>
 *   <li>Nombre del método</li>
 *   <li>Parámetros de entrada</li>
 *   <li>Tiempo de ejecución</li>
 *   <li>Excepciones si ocurren</li>
 * </ul>
 * </p>
 *
 * <p>Debe estar respaldada por un aspecto que implemente la lógica de logging.</p>
 *
 * @see com.reclutamiento.seguimientoSeleccion.logging.LoggingAspect
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
}
