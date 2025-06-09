package com.reclutamiento.seguimientoSeleccion.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada lanzada cuando no se encuentra un recurso solicitado.
 * <p>
 * Se utiliza para lanzar errores HTTP 404 (Not Found) de forma controlada.
 */
@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * Nombre del recurso que no se encontró (por ejemplo, "Candidato").
     */
    private final String resourceName;

    /**
     * Nombre del campo que se utilizó para buscar el recurso (por ejemplo, "id").
     */
    private final String fieldName;

    /**
     * Valor del campo utilizado en la búsqueda.
     */
    private final Object fieldValue;

    /**
     * Constructor que permite lanzar la excepción con un mensaje personalizado.
     *
     * @param message Mensaje de error descriptivo.
     */
    public NotFoundException(String message) {
        super(message);
        this.resourceName = null;
        this.fieldName = null;
        this.fieldValue = null;
    }

    /**
     * Constructor que construye automáticamente un mensaje de error
     * indicando qué recurso no se encontró y con qué criterio.
     *
     * @param resourceName Nombre del recurso (por ejemplo, "Usuario").
     * @param fieldName    Campo de búsqueda (por ejemplo, "email").
     * @param fieldValue   Valor del campo buscado (por ejemplo, "correo@ejemplo.com").
     */
    public NotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s: '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
