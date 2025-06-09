package com.reclutamiento.seguimientoSeleccion.repository;

import com.reclutamiento.seguimientoSeleccion.model.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Candidato}.
 * <p>
 * Extiende {@link JpaRepository}, lo que proporciona automáticamente
 * métodos CRUD, paginación y consultas básicas sobre la entidad.
 * <p>
 * Spring Data JPA genera la implementación en tiempo de ejecución.
 */
@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
}
