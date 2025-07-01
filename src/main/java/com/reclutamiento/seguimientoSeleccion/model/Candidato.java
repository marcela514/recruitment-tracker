package com.reclutamiento.seguimientoSeleccion.model;

import com.reclutamiento.seguimientoSeleccion.enums.EstadoCandidato;
import com.reclutamiento.seguimientoSeleccion.enums.Genero;
import com.reclutamiento.seguimientoSeleccion.enums.NivelEducativo;
import com.reclutamiento.seguimientoSeleccion.enums.TipoDocumento;
import com.reclutamiento.seguimientoSeleccion.model.audit.Auditable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

/**
 * Entidad que representa a un candidato en el sistema de seguimiento de selección.
 * <p>
 * Incluye información personal, de contacto, educativa y de auditoría. También se indexan
 * algunos campos relevantes para mejorar el rendimiento en consultas frecuentes.
 * </p>
 *
 * <p>Extiende {@link Auditable} para heredar los campos de auditoría general,
 * y define campos adicionales propios de auditoría explícita como {@code creadoPor} y {@code fechaCreacion}.</p>
 *
 * <p>Los valores por defecto para el estado y la fecha de registro se asignan en el método {@code prePersist()}.</p>
 *
 * @see EstadoCandidato
 * @see Genero
 * @see NivelEducativo
 * @see TipoDocumento
 * @see Auditable
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "candidato",
        indexes = {
                @Index(name = "idx_documento", columnList = "documentoIdentidad"),
                @Index(name = "idx_email", columnList = "email"),
                @Index(name = "idx_estado", columnList = "estado")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidato extends Auditable {

    /**
     * Identificador único del candidato (clave primaria).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre completo del candidato.
     */
    @Column(nullable = false, length = 100)
    private String nombre;

    /**
     * Correo electrónico del candidato. Debe ser único.
     */
    @Column(nullable = false, length = 100, unique = true)
    private String email;

    /**
     * Número de teléfono del candidato.
     */
    @Column(nullable = false, length = 15)
    private String telefono;

    /**
     * Tipo de documento de identidad (Cédula, Pasaporte, etc.).
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoDocumento tipoDocumento;

    /**
     * Número del documento de identidad. Debe ser único.
     */
    @Column(nullable = false, length = 20, unique = true)
    private String documentoIdentidad;

    /**
     * País de residencia del candidato.
     */
    @Column(nullable = false, length = 50)
    private String paisResidencia;

    /**
     * Ciudad de residencia del candidato.
     */
    @Column(nullable = false, length = 50)
    private String ciudadResidencia;

    /**
     * Fecha de nacimiento del candidato.
     */
    @Column(nullable = false)
    private LocalDate fechaNacimiento;

    /**
     * Género del candidato.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Genero genero;

    /**
     * Nivel educativo más alto alcanzado por el candidato.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelEducativo nivelEducativo;

    /**
     * URL del perfil de LinkedIn del candidato (opcional).
     */
    @Column(length = 255)
    private String linkedinUrl;

    /**
     * Estado actual del candidato dentro del proceso de selección.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCandidato estado;

    /**
     * Fecha en la que el candidato fue registrado en el sistema.
     * Se asigna automáticamente si no se establece manualmente.
     */
    @Column(nullable = false)
    private LocalDate fechaRegistro;

    /**
     * Método de ciclo de vida de JPA que se ejecuta antes de persistir por primera vez.
     * <p>
     * Establece valores por defecto si no fueron asignados previamente:
     * <ul>
     *   <li>{@link #fechaRegistro} con la fecha actual</li>
     *   <li>{@link #estado} con {@code EstadoCandidato.ACTIVO}</li>
     * </ul>
     * </p>
     */
    @PrePersist
    public void prePersist() {
        if (this.fechaRegistro == null) {
            this.fechaRegistro = LocalDate.now();
        }
        if (this.estado == null) {
            this.estado = EstadoCandidato.ACTIVO;
        }
    }
}
