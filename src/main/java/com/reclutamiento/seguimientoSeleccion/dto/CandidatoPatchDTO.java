package com.reclutamiento.seguimientoSeleccion.dto;

import com.reclutamiento.seguimientoSeleccion.enums.EstadoCandidato;
import com.reclutamiento.seguimientoSeleccion.enums.Genero;
import com.reclutamiento.seguimientoSeleccion.enums.NivelEducativo;
import com.reclutamiento.seguimientoSeleccion.enums.TipoDocumento;
import com.reclutamiento.seguimientoSeleccion.logging.Sensitive;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO utilizado para la actualización parcial de los datos de un candidato.
 * Todos los campos son opcionales y se validan si se proporcionan.
 */
@Data
public class CandidatoPatchDTO {

    /**
     * Nombre completo del candidato.
     * Máximo 100 caracteres.
     */
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo debe contener letras y espacios")
    private String nombre;

    /**
     * Correo electrónico del candidato.
     * Debe tener un formato válido y no superar los 100 caracteres.
     */
    @Sensitive
    @Email(message = "El correo electrónico debe tener un formato válido")
    @Size(max = 100, message = "El correo electrónico no debe superar los 100 caracteres")
    private String email;

    /**
     * Número de teléfono del candidato.
     * Entre 7 y 15 dígitos, puede comenzar con '+'.
     */
    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "El número de teléfono debe tener entre 7 y 15 dígitos y puede comenzar con '+'"
    )
    @Sensitive
    private String telefono;

    /**
     * Tipo de documento del candidato.
     * Campo opcional en la actualización.
     */
    private TipoDocumento tipoDocumento;

    /**
     * Número de documento de identidad del candidato.
     * Máximo 20 caracteres.
     */
    @Sensitive
    @Size(max = 20, message = "El número de documento no debe superar los 20 caracteres")
    @Pattern(regexp = "^[0-9]{5,20}$", message = "El documento debe contener solo números entre 5 y 20 dígitos")
    private String documentoIdentidad;

    /**
     * País de residencia del candidato.
     * Máximo 50 caracteres.
     */
    @Size(max = 50, message = "El país no debe superar los 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "El país solo debe contener letras y espacios")
    private String paisResidencia;

    /**
     * Ciudad de residencia del candidato.
     * Máximo 50 caracteres.
     */
    @Size(max = 50, message = "La ciudad no debe superar los 50 caracteres")
    @Pattern(regexp = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", message = "La ciudad solo debe contener letras y espacios")
    private String ciudadResidencia;

    /**
     * Fecha de nacimiento del candidato.
     * Debe ser una fecha en el pasado.
     */
    @Past(message = "La fecha de nacimiento debe ser una fecha en el pasado")
    private LocalDate fechaNacimiento;

    /**
     * Género del candidato.
     * Campo opcional.
     */
    private Genero genero;

    /**
     * Nivel educativo alcanzado por el candidato.
     * Campo opcional.
     */
    private NivelEducativo nivelEducativo;

    /**
     * URL del perfil de LinkedIn del candidato.
     * Máximo 255 caracteres y debe tener formato válido de LinkedIn.
     */
    @Size(max = 255, message = "La URL de LinkedIn no debe superar los 255 caracteres")
    @Pattern(
            regexp = "^(https?://)?(www\\.)?linkedin\\.com/(in|pub)/[a-zA-Z0-9\\-_%]+/?$",
            message = "La URL debe ser válida y provenir de LinkedIn"
    )
    private String linkedinUrl;

    /**
     * Estado actual del candidato en el proceso de selección.
     * Campo opcional.
     */
    private EstadoCandidato estado;
}
