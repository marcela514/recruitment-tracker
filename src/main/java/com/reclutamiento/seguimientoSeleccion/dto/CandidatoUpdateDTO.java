package com.reclutamiento.seguimientoSeleccion.dto;

import com.reclutamiento.seguimientoSeleccion.enums.EstadoCandidato;
import com.reclutamiento.seguimientoSeleccion.enums.Genero;
import com.reclutamiento.seguimientoSeleccion.enums.NivelEducativo;
import com.reclutamiento.seguimientoSeleccion.enums.TipoDocumento;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CandidatoUpdateDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombre;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "Debe ser un correo electrónico válido")
    @Size(max = 100, message = "El correo no debe superar los 100 caracteres")
    private String email;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Debe tener entre 7 y 15 dígitos, y puede iniciar con +")
    private String telefono;

    @NotNull(message = "El tipo de documento es obligatorio")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    @Size(max = 20, message = "El documento no debe superar los 20 caracteres")
    private String documentoIdentidad;

    @NotBlank(message = "El país de residencia es obligatorio")
    @Size(max = 50, message = "El país no debe superar los 50 caracteres")
    private String paisResidencia;

    @NotBlank(message = "La ciudad de residencia es obligatoria")
    @Size(max = 50, message = "La ciudad no debe superar los 50 caracteres")
    private String ciudadResidencia;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El género es obligatorio")
    private Genero genero;

    @NotNull(message = "El nivel educativo es obligatorio")
    private NivelEducativo nivelEducativo;

    @Size(max = 255, message = "La URL de LinkedIn no debe superar los 255 caracteres")
    @Pattern(regexp = "^(https?://)?(www\\.)?linkedin\\.com/.*$",
            message = "Debe ser una URL válida de LinkedIn")
    private String linkedinUrl;

    @NotNull(message = "El estado del candidato es obligatorio")
    private EstadoCandidato estado;
}
