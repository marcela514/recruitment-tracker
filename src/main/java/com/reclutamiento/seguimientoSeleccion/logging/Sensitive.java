package com.reclutamiento.seguimientoSeleccion.logging;

import java.lang.annotation.*;

/**
 * Anotación para marcar campos que contienen información sensible
 * y que no deben ser mostrados directamente en los logs.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sensitive {
}
