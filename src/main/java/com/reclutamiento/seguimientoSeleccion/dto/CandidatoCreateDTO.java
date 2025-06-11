package com.reclutamiento.seguimientoSeleccion.dto;

import com.reclutamiento.seguimientoSeleccion.enums.Genero;
import com.reclutamiento.seguimientoSeleccion.enums.NivelEducativo;
import com.reclutamiento.seguimientoSeleccion.enums.TipoDocumento;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO utilizado para la creación de un nuevo candidato.
 * Contiene los datos personales y académicos requeridos
 * para registrar un candidato en el sistema.
 */
@Data
public class CandidatoCreateDTO {

    /**
     * Nombre completo del candidato.
     * Debe ser un texto no vacío de máximo 100 caracteres.
     */
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombre;

    /**
     * Correo electrónico del candidato.
     * Debe tener un formato válido y no exceder los 100 caracteres.
     */
    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    @Size(max = 100, message = "El correo electrónico no debe superar los 100 caracteres")
    private String email;

    /**
     * Número de teléfono del candidato.
     * Debe contener entre 7 y 15 dígitos, y puede comenzar con '+'.
     */
    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(
            regexp = "^\\+?[0-9]{7,15}$",
            message = "El número de teléfono debe tener entre 7 y 15 dígitos y puede comenzar con '+'"
    )
    private String telefono;

    /**
     * Tipo de documento de identidad del candidato.
     * Es un valor requerido.
     */
    @NotNull(message = "El tipo de documento es obligatorio")
    private TipoDocumento tipoDocumento;

    /**
     * Número de documento de identidad.
     * Campo obligatorio con un máximo de 20 caracteres.
     */
    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El número de documento no debe superar los 20 caracteres")
    private String documentoIdentidad;

    /**
     * País de residencia actual del candidato.
     * No debe superar los 50 caracteres.
     */
    @NotBlank(message = "El país de residencia es obligatorio")
    @Size(max = 50, message = "El país no debe superar los 50 caracteres")
    private String paisResidencia;

    /**
     * Ciudad de residencia actual del candidato.
     * No debe superar los 50 caracteres.
     */
    @NotBlank(message = "La ciudad de residencia es obligatoria")
    @Size(max = 50, message = "La ciudad no debe superar los 50 caracteres")
    private String ciudadResidencia;

    /**
     * Fecha de nacimiento del candidato.
     * Debe ser una fecha en el pasado.
     */
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha en el pasado")
    private LocalDate fechaNacimiento;

    /**
     * Género del candidato.
     * Campo requerido.
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
     * URL del perfil de LinkedIn del candidato (opcional).
     * Si se proporciona, debe ser una URL válida de LinkedIn y no superar los 255 caracteres.
     */
    @Size(max = 255, message = "La URL de LinkedIn no debe superar los 255 caracteres")
    @Pattern(
            regexp = "^(https?://)?(www\\.)?linkedin\\.com/.*$",
            message = "La URL debe ser válida y provenir de LinkedIn"
    )
    private String linkedinUrl;
}
