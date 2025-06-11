package com.reclutamiento.seguimientoSeleccion.dto;

import com.reclutamiento.seguimientoSeleccion.enums.EstadoCandidato;
import com.reclutamiento.seguimientoSeleccion.enums.Genero;
import com.reclutamiento.seguimientoSeleccion.enums.NivelEducativo;
import com.reclutamiento.seguimientoSeleccion.enums.TipoDocumento;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO utilizado para la actualización completa de un candidato existente en el sistema.
 * Incluye todos los campos requeridos y validados para mantener la integridad de los datos.
 */
@Data
public class CandidatoUpdateDTO {

    /**
     * Nombre completo del candidato.
     * No debe estar vacío y tiene un máximo de 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombre;

    /**
     * Correo electrónico del candidato.
     * Debe tener un formato válido y no superar los 100 caracteres.
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    @Size(max = 100, message = "El correo no debe superar los 100 caracteres")
    private String email;

    /**
     * Número de teléfono del candidato.
     * Debe tener entre 7 y 15 dígitos, opcionalmente comenzando con '+'.
     */
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(
            regexp = "^\\+?\\d{7,15}$",
            message = "Debe tener entre 7 y 15 dígitos, y puede iniciar con +"
    )
    private String telefono;

    /**
     * Tipo de documento de identidad del candidato.
     * No puede ser nulo.
     */
    @NotNull(message = "El tipo de documento es obligatorio")
    private TipoDocumento tipoDocumento;

    /**
     * Número del documento de identidad del candidato.
     * No puede estar vacío y tiene un máximo de 20 caracteres.
     */
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El documento no debe superar los 20 caracteres")
    private String documentoIdentidad;

    /**
     * País de residencia del candidato.
     * Campo obligatorio, máximo 50 caracteres.
     */
    @NotBlank(message = "El país de residencia es obligatorio")
    @Size(max = 50, message = "El país no debe superar los 50 caracteres")
    private String paisResidencia;

    /**
     * Ciudad de residencia del candidato.
     * Campo obligatorio, máximo 50 caracteres.
     */
    @NotBlank(message = "La ciudad de residencia es obligatoria")
    @Size(max = 50, message = "La ciudad no debe superar los 50 caracteres")
    private String ciudadResidencia;

    /**
     * Fecha de nacimiento del candidato.
     * Debe ser una fecha válida en el pasado.
     */
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    /**
     * Género del candidato.
     * Campo obligatorio.
     */
    @NotNull(message = "El género es obligatorio")
    private Genero genero;

    /**
     * Nivel educativo alcanzado por el candidato.
     * Campo obligatorio.
     */
    @NotNull(message = "El nivel educativo es obligatorio")
    private NivelEducativo nivelEducativo;

    /**
     * URL del perfil de LinkedIn del candidato.
     * Puede estar vacía o ser una URL válida de LinkedIn, máximo 255 caracteres.
     */
    @Size(max = 255, message = "La URL de LinkedIn no debe superar los 255 caracteres")
    @Pattern(
            regexp = "^(|https?://(www\\.)?linkedin\\.com/.*)$",
            message = "Debe ser una URL válida de LinkedIn o estar vacía"
    )
    private String linkedinUrl;

    /**
     * Estado actual del candidato dentro del proceso de selección.
     * Campo obligatorio.
     */
    @NotNull(message = "El estado del candidato es obligatorio")
    private EstadoCandidato estado;
}
