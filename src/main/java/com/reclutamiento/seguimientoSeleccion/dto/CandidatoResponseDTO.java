package com.reclutamiento.seguimientoSeleccion.dto;

import lombok.Data;

@Data
public class CandidatoResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String tipoDocumento;
    private String tipoDocumentoLabel;
    private String documentoIdentidad;
    private String paisResidencia;
    private String ciudadResidencia;
    private String fechaNacimiento;
    private String genero;
    private String generoLabel;
    private String nivelEducativo;
    private String nivelEducativoLabel;
    private String linkedinUrl;
    private String estado;
    private String estadoLabel;
    private String fechaRegistro;
}
