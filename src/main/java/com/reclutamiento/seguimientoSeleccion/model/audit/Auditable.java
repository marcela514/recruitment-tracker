package com.reclutamiento.seguimientoSeleccion.model.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Clase base abstracta para entidades que requieren auditoría automática
 * de campos como fecha de creación, última modificación y usuarios responsables.
 * <p>
 * Esta clase debe ser extendida por otras entidades JPA para heredar el comportamiento
 * de auditoría provisto por Spring Data JPA.
 * </p>
 *
 * <p>
 * Los campos se manejan automáticamente mediante {@link AuditingEntityListener},
 * y requieren que la auditoría JPA esté habilitada en la aplicación mediante
 * la anotación {@link org.springframework.data.jpa.repository.config.EnableJpaAuditing}
 * en la clase principal.
 * </p>
 *
 * @see org.springframework.data.jpa.repository.config.EnableJpaAuditing
 * @see com.reclutamiento.seguimientoSeleccion.config.AuditConfig
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {

    /**
     * Fecha y hora en que se creó la entidad.
     * Este campo es establecido automáticamente al persistir por primera vez.
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    /**
     * Fecha y hora de la última modificación de la entidad.
     * Este campo se actualiza automáticamente cada vez que la entidad es modificada.
     */
    @LastModifiedDate
    private LocalDateTime updatedAt;

    /**
     * Identificador del usuario que creó la entidad.
     * Este campo es establecido automáticamente al persistir por primera vez.
     */
    @CreatedBy
    @Column(updatable = false)
    private String createdBy;

    /**
     * Identificador del usuario que modificó por última vez la entidad.
     * Este campo se actualiza automáticamente con cada modificación.
     */
    @LastModifiedBy
    private String modifiedBy;
}
