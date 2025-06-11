package com.reclutamiento.seguimientoSeleccion.dto;

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
    private String email;

    /**
     * Número de teléfono del candidato.
     */
    private String telefono;

    /**
     * Tipo de documento del candidato (valor técnico).
     */
    private String tipoDocumento;

    /**
     * Etiqueta legible del tipo de documento.
     */
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
}
