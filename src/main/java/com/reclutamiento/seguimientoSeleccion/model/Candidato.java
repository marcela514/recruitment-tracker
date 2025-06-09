package com.reclutamiento.seguimientoSeleccion.model;

import com.reclutamiento.seguimientoSeleccion.enums.EstadoCandidato;
import com.reclutamiento.seguimientoSeleccion.enums.Genero;
import com.reclutamiento.seguimientoSeleccion.enums.NivelEducativo;
import com.reclutamiento.seguimientoSeleccion.enums.TipoDocumento;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "candidato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String email;

    private String telefono;

    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;

    private String documentoIdentidad;

    private String paisResidencia;

    private String ciudadResidencia;

    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Enumerated(EnumType.STRING)
    private NivelEducativo nivelEducativo;

    private String linkedinUrl;

    @Enumerated(EnumType.STRING)
    private EstadoCandidato estado;

    private LocalDate fechaRegistro;

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
