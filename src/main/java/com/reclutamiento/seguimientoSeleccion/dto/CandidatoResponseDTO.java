package com.reclutamiento.seguimientoSeleccion.dto;

import com.reclutamiento.seguimientoSeleccion.logging.Sensitive;
import lombok.Data;

/**
 * DTO utilizado para representar la información de un candidato
 * en las respuestas del sistema.
 * Contiene datos personales, académicos y de estado dentro del proceso de selección.
 */
@Data
public class CandidatoResponseDTO {

    /**
     * Identificador único del candidato.
     */
    private Long id;

    /**
     * Nombre completo del candidato.
     */
    private String nombre;

    /**
     * Correo electrónico del candidato.
     */
    @Sensitive
    private String email;

    /**
     * Número de teléfono del candidato.
     */
    @Sensitive
    private String telefono;

    /**
     * Tipo de documento del candidato (valor técnico).
     */
    private String tipoDocumento;

    /**
     * Etiqueta legible del tipo de documento.
     */
    @Sensitive
    private String tipoDocumentoLabel;

    /**
     * Número de documento de identidad del candidato.
     */
    private String documentoIdentidad;

    /**
     * País de residencia del candidato.
     */
    private String paisResidencia;

    /**
     * Ciudad de residencia del candidato.
     */
    private String ciudadResidencia;

    /**
     * Fecha de nacimiento del candidato, en formato de texto.
     */
    private String fechaNacimiento;

    /**
     * Género del candidato (valor técnico).
     */
    private String genero;

    /**
     * Etiqueta legible del género del candidato.
     */
    private String generoLabel;

    /**
     * Nivel educativo alcanzado por el candidato (valor técnico).
     */
    private String nivelEducativo;

    /**
     * Etiqueta legible del nivel educativo.
     */
    private String nivelEducativoLabel;

    /**
     * URL del perfil de LinkedIn del candidato, si fue proporcionada.
     */
    private String linkedinUrl;

    /**
     * Estado actual del candidato en el proceso de selección (valor técnico).
     */
    private String estado;

    /**
     * Etiqueta legible del estado del candidato.
     */
    private String estadoLabel;

    /**
     * Fecha en la que se registró el candidato en el sistema, en formato de texto.
     */
    private String fechaRegistro;

    /**
     * Nombre del usuario que creó el registro.
     */
    private String creadoPor;

    /**
     * Fecha y hora en que se creó el registro.
     * Representada como texto (por ejemplo, formato ISO 8601).
     */
    private String fechaCreacion;

    /**
     * Nombre del usuario que realizó la última modificación del registro.
     */
    private String modificadoPor;

    /**
     * Fecha y hora de la última modificación del registro.
     * Representada como texto (por ejemplo, formato ISO 8601).
     */
    private String fechaModificacion;

}
