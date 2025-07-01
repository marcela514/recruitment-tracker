package com.reclutamiento.seguimientoSeleccion.logging;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Utilidad para generar una representación en texto de un objeto,
 * ocultando los valores de campos marcados como sensibles mediante la anotación {@link Sensitive}.
 * Compatible con DTOs, listas, mapas y estructuras anidadas simples.
 */
public class SensitiveFieldMasker {

    private static final String MASK = "***";
    private static final String DTO_PACKAGE = "com.reclutamiento.seguimientoSeleccion.dto";

    /**
     * Genera una representación en texto del objeto, ocultando campos marcados como {@link Sensitive}.
     * Recorre recursivamente listas y objetos DTO anidados, previniendo ciclos.
     *
     * @param obj objeto a procesar
     * @return texto con los valores sensibles enmascarados
     */
    public static String mask(Object obj) {
        return mask(obj, new IdentityHashMap<>());
    }

    private static String mask(Object obj, Map<Object, Boolean> visited) {
        if (obj == null) return "null";
        if (visited.containsKey(obj)) return "(circular)";
        visited.put(obj, true);

        if (obj instanceof Collection<?> collection) {
            return maskCollection(collection, visited);
        }

        if (!isDto(obj)) return String.valueOf(obj);

        Class<?> clazz = obj.getClass();
        StringBuilder sb = new StringBuilder(clazz.getSimpleName()).append("{");

        Field[] fields;
        try {
            fields = clazz.getDeclaredFields();
        } catch (SecurityException e) {
            return clazz.getSimpleName() + "{<acceso denegado>}";
        }

        for (Field field : fields) {
            // Se fuerza el acceso para leer campos privados en DTOs
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                sb.append(field.getName()).append("=");
                if (field.isAnnotationPresent(Sensitive.class)) {
                    sb.append(MASK);
                } else {
                    sb.append(mask(value, visited));
                }
                sb.append(", ");
            } catch (IllegalAccessException e) {
                sb.append(field.getName()).append("=ERROR, ");
            }
        }

        if (sb.toString().endsWith(", ")) {
            sb.setLength(sb.length() - 2); // Elimina la coma final
        }

        sb.append("}");
        return sb.toString();
    }

    private static String maskCollection(Collection<?> collection, Map<Object, Boolean> visited) {
        StringBuilder sb = new StringBuilder("[");
        for (Object item : collection) {
            sb.append(mask(item, visited)).append(", ");
        }
        if (sb.toString().endsWith(", ")) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Determina si un objeto pertenece al paquete de DTOs definidos por la aplicación.
     *
     * @param obj objeto a evaluar
     * @return true si es un DTO, false si no
     */
    public static boolean isDto(Object obj) {
        return obj != null && obj.getClass().getName().startsWith(DTO_PACKAGE);
    }
}
