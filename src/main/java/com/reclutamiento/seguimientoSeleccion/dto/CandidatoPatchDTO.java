package com.reclutamiento.seguimientoSeleccion.dto;

import com.reclutamiento.seguimientoSeleccion.enums.EstadoCandidato;
import com.reclutamiento.seguimientoSeleccion.enums.Genero;
import com.reclutamiento.seguimientoSeleccion.enums.NivelEducativo;
import com.reclutamiento.seguimientoSeleccion.enums.TipoDocumento;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CandidatoPatchDTO {

    @Size(max = 100, message = "El nombre no debe superar los 100 caracteres")
    private String nombre;

    @Email(message = "Debe ser un correo electrónico válido")
    @Size(max = 100, message = "El correo no debe superar los 100 caracteres")
    private String email;

    @Pattern(regexp = "^\\+?\\d{7,15}$", message = "Debe tener entre 7 y 15 dígitos, y puede iniciar con +")
    private String telefono;

    private TipoDocumento tipoDocumento;

    @Size(max = 20, message = "El documento no debe superar los 20 caracteres")
    private String documentoIdentidad;

    @Size(max = 50, message = "El país no debe superar los 50 caracteres")
    private String paisResidencia;

    @Size(max = 50, message = "La ciudad no debe superar los 50 caracteres")
    private String ciudadResidencia;

    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    private Genero genero;

    private NivelEducativo nivelEducativo;

    @Size(max = 255, message = "La URL de LinkedIn no debe superar los 255 caracteres")
    @Pattern(regexp = "^(https?://)?(www\\.)?linkedin\\.com/.*$",
            message = "Debe ser una URL válida de LinkedIn")
    private String linkedinUrl;

    private EstadoCandidato estado;
}
