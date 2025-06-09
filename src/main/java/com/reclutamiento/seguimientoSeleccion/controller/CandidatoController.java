package com.reclutamiento.seguimientoSeleccion.controller;

import com.reclutamiento.seguimientoSeleccion.dto.*;
import com.reclutamiento.seguimientoSeleccion.service.AsyncExportService;
import com.reclutamiento.seguimientoSeleccion.service.CandidatoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.concurrent.CompletableFuture;

/**
 * Controlador REST para la gestión de candidatos en el proceso de selección.
 * Proporciona endpoints para operaciones CRUD completas y parciales,
 * así como exportación asincrónica de datos.
 */
@RestController
@RequestMapping("/api/candidatos")
@Validated
public class CandidatoController {

    private final CandidatoService candidatoService;
    private final AsyncExportService asyncExportService;

    @Autowired
    public CandidatoController(CandidatoService candidatoService, AsyncExportService asyncExportService) {
        this.candidatoService = candidatoService;
        this.asyncExportService = asyncExportService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<CandidatoResponseDTO>> getAllCandidatos(Pageable pageable) {
        PagedResponse<CandidatoResponseDTO> response = candidatoService.getAllCandidatos(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> getCandidatoById(@PathVariable Long id) {
        CandidatoResponseDTO dto = candidatoService.getCandidatoById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CandidatoResponseDTO> createCandidato(@Valid @RequestBody CandidatoCreateDTO candidatoDTO) {
        CandidatoResponseDTO saved = candidatoService.saveCandidato(candidatoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> updateCandidato(
            @PathVariable Long id,
            @Valid @RequestBody CandidatoUpdateDTO candidatoDTO) {
        CandidatoResponseDTO updated = candidatoService.updateCandidato(id, candidatoDTO);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> actualizarParcialCandidato(
            @PathVariable Long id,
            @Valid @RequestBody CandidatoPatchDTO patchDto) {
        CandidatoResponseDTO actualizado = candidatoService.actualizarParcial(id, patchDto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCandidato(@PathVariable Long id) {
        candidatoService.deleteCandidato(id);
    }

    @PostMapping("/export/async")
    public CompletableFuture<ResponseEntity<String>> exportarCandidatosAsync(
            @RequestParam String format,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size) {

        Pageable pageable = PageRequest.of(page, size);
        return candidatoService.exportarCandidatosAsync(format, pageable)
                .thenApply(exportId -> ResponseEntity.accepted()
                        .body("Exportación en proceso. ID: " + exportId));
    }

    @GetMapping("/export/async/{exportId}")
    public ResponseEntity<?> obtenerExportacionAsync(@PathVariable String exportId) {
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
