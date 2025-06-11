package com.reclutamiento.seguimientoSeleccion.controller;

import com.reclutamiento.seguimientoSeleccion.dto.*;
import com.reclutamiento.seguimientoSeleccion.service.AsyncExportService;
import com.reclutamiento.seguimientoSeleccion.service.CandidatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

/**
 * Controlador REST para la gestión de candidatos en el proceso de selección.
 * Provee endpoints para CRUD, actualización parcial y exportación de candidatos.
 */
@RestController
@RequestMapping("/api/candidatos")
@Validated
public class CandidatoController {

    private final CandidatoService candidatoService;
    private final AsyncExportService asyncExportService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param candidatoService servicio que maneja la lógica de negocio relacionada con candidatos
     * @param asyncExportService servicio encargado de la exportación asíncrona
     */
    @Autowired
    public CandidatoController(CandidatoService candidatoService, AsyncExportService asyncExportService) {
        this.candidatoService = candidatoService;
        this.asyncExportService = asyncExportService;
    }

    /**
     * Obtiene una lista paginada de todos los candidatos.
     *
     * @param pageable información de paginación (página, tamaño, orden)
     * @return lista paginada de candidatos
     */
    @GetMapping
    public ResponseEntity<PagedResponse<CandidatoResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(candidatoService.getAllCandidatos(pageable));
    }

    /**
     * Obtiene los detalles de un candidato por su ID.
     *
     * @param id identificador del candidato
     * @return candidato correspondiente al ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(candidatoService.getCandidatoById(id));
    }

    /**
     * Crea un nuevo candidato.
     *
     * @param dto datos del candidato a crear
     * @return candidato creado
     */
    @PostMapping
    public ResponseEntity<CandidatoResponseDTO> create(@Valid @RequestBody CandidatoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(candidatoService.saveCandidato(dto));
    }

    /**
     * Actualiza completamente un candidato existente por su ID.
     *
     * @param id identificador del candidato
     * @param dto datos actualizados del candidato
     * @return candidato actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CandidatoUpdateDTO dto) {
        return ResponseEntity.ok(candidatoService.updateCandidato(id, dto));
    }

    /**
     * Actualiza parcialmente los datos de un candidato por su ID.
     *
     * @param id identificador del candidato
     * @param dto campos a actualizar del candidato
     * @return candidato actualizado parcialmente
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> patch(
            @PathVariable Long id,
            @Valid @RequestBody CandidatoPatchDTO dto) {
        return ResponseEntity.ok(candidatoService.actualizarParcial(id, dto));
    }

    /**
     * Elimina un candidato por su ID.
     *
     * @param id identificador del candidato
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        candidatoService.deleteCandidato(id);
    }

    /**
     * Inicia una exportación asíncrona de candidatos en el formato especificado.
     *
     * @param format formato de exportación: pdf, excel o csv
     * @param page página de resultados a exportar
     * @param size tamaño de página
     * @return mensaje de aceptación con el ID de la exportación
     */
    @PostMapping("/export/async")
    public CompletableFuture<ResponseEntity<String>> exportAsync(
            @RequestParam String format,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return candidatoService.exportarCandidatosAsync(format, pageable)
                .thenApply(id -> ResponseEntity.accepted()
                        .body("Exportación en proceso. ID: " + id));
    }

    /**
     * Obtiene el resultado de una exportación asíncrona previamente iniciada.
     *
     * @param exportId identificador de la exportación
     * @return archivo exportado si está disponible, o mensaje de estado
     */
    @GetMapping("/export/async/{exportId}")
    public ResponseEntity<?> getExportAsync(@PathVariable String exportId) {
        ExportResult result = asyncExportService.getExportResult(exportId);

        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Exportación no encontrada o aún en proceso.");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + result.filename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, result.contentType())
                .body(result.data());
    }
}
