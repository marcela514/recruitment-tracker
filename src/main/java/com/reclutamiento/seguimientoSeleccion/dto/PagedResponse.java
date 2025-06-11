package com.reclutamiento.seguimientoSeleccion.dto;

import org.springframework.data.domain.Page;
import java.util.List;

/**
 * DTO genérico para representar una respuesta paginada.
 *
 * @param <T> Tipo de los elementos contenidos en la página.
 */
public record PagedResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last
) {
    /**
     * Crea una instancia de PagedResponse a partir de un objeto Page de Spring.
     *
     * @param page objeto Page con la información paginada.
     * @param <T>  tipo de los elementos en la página.
     * @return instancia de PagedResponse
     */
    public static <T> PagedResponse<T> fromPage(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
