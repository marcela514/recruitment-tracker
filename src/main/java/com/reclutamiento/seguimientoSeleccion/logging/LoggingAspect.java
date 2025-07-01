package com.reclutamiento.seguimientoSeleccion.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Aspecto responsable de registrar automáticamente la ejecución de métodos
 * anotados con {@link Loggable} o definidos dentro de clases anotadas con {@link Loggable}.
 *
 * <p>Registra información como:</p>
 * <ul>
 *     <li>Nombre de la clase y método ejecutado</li>
 *     <li>Argumentos de entrada (con datos sensibles ofuscados)</li>
 *     <li>Duración de la ejecución</li>
 *     <li>Excepciones lanzadas</li>
 *     <li>Resultado del método (con datos sensibles ofuscados si aplica)</li>
 * </ul>
 *
 * @see Loggable
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("@annotation(com.reclutamiento.seguimientoSeleccion.logging.Loggable) || " +
            "@within(com.reclutamiento.seguimientoSeleccion.logging.Loggable)")
    public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String className = signature.getDeclaringType().getSimpleName();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();

        String argList = Arrays.stream(args)
                .map(this::maskIfApplicable)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        logger.info("➡️  Ejecutando {}.{}({})", className, methodName, argList);

        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - startTime;

            logger.info("✅ {}.{} completado en {} ms", className, methodName, elapsed);

            if (!signature.getReturnType().equals(void.class)) {
                String resultLog = maskIfApplicable(result);
                logger.debug("🔁 Resultado: {}", resultLog);
            }

            return result;
        } catch (Throwable ex) {
            long elapsed = System.currentTimeMillis() - startTime;
            logger.error("❌ Error en {}.{} después de {} ms: {}", className, methodName, elapsed, ex.getMessage(), ex);
            throw ex;
        }
    }

    /**
     * Aplica el enmascaramiento si el objeto es un DTO, una colección/mapa de DTOs o un array.
     *
     * @param obj objeto a evaluar
     * @return representación en texto del objeto (con campos sensibles ocultos si aplica)
     */
    private String maskIfApplicable(Object obj) {
        if (obj == null) return "null";

        if (SensitiveFieldMasker.isDto(obj)) {
            return SensitiveFieldMasker.mask(obj);
        }

        if (obj instanceof Collection<?> collection) {
            return collection.stream()
                    .map(this::maskIfApplicable)
                    .reduce((a, b) -> a + ", " + b)
                    .map(str -> "[" + str + "]")
                    .orElse("[]");
        }

        if (obj instanceof Map<?, ?> map) {
            StringBuilder sb = new StringBuilder("{");
            map.forEach((k, v) -> sb.append(k).append("=").append(maskIfApplicable(v)).append(", "));
            if (sb.lastIndexOf(", ") == sb.length() - 2) {
                sb.setLength(sb.length() - 2);
            }
            sb.append("}");
            return sb.toString();
        }

        if (obj.getClass().isArray()) {
            return Arrays.stream((Object[]) obj)
                    .map(this::maskIfApplicable)
                    .reduce((a, b) -> a + ", " + b)
                    .map(str -> "[" + str + "]")
                    .orElse("[]");
        }

        return String.valueOf(obj);
    }
}
