package com.reclutamiento.seguimientoSeleccion.util;

import com.reclutamiento.seguimientoSeleccion.enums.LocalizableEnum;
import org.springframework.context.MessageSource;

import java.util.Locale;

public class EnumLabelUtil {

    /**
     * Devuelve la etiqueta localizada para un enum que implementa LocalizableEnum.
     * Si no hay traducción disponible, retorna el nombre del enum como valor por defecto.
     *
     * @param e             Enum que implementa LocalizableEnum
     * @param messageSource Fuente de mensajes de Spring
     * @param locale        Localización actual
     * @return Label traducido o el nombre del enum si no se encuentra el mensaje
     */
    public static String getLabel(LocalizableEnum e, MessageSource messageSource, Locale locale) {
        if (e == null) return null;

        // Usamos casting seguro para acceder a .name()
        String fallback = e instanceof Enum ? ((Enum<?>) e).name() : e.getMessageKey();

        return messageSource.getMessage(e.getMessageKey(), null, fallback, locale);
    }
}
