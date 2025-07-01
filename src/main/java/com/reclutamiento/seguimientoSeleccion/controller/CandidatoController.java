package com.reclutamiento.seguimientoSeleccion.controller;

import com.reclutamiento.seguimientoSeleccion.dto.*;
import com.reclutamiento.seguimientoSeleccion.logging.Loggable;
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
 * <p>
 * Proporciona endpoints para operaciones CRUD, actualizaciones parciales y exportaciones
 * de candidatos, incluyendo exportaciones asíncronas.
 * </p>
 *
 * @see com.reclutamiento.seguimientoSeleccion.service.CandidatoService
 * @see com.reclutamiento.seguimientoSeleccion.dto.CandidatoResponseDTO
 * @see com.reclutamiento.seguimientoSeleccion.dto.ExportRequestDTO
 */
@Loggable
@RestController
@RequestMapping("/api/candidatos")
@Validated
public class CandidatoController {

    private final CandidatoService candidatoService;
    private final AsyncExportService asyncExportService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param candidatoService    servicio que maneja la lógica de negocio relacionada con candidatos
     * @param asyncExportService  servicio encargado de la exportación asíncrona de candidatos
     */
    @Autowired
    public CandidatoController(CandidatoService candidatoService, AsyncExportService asyncExportService) {
        this.candidatoService = candidatoService;
        this.asyncExportService = asyncExportService;
    }

    /**
     * Obtiene una lista paginada de todos los candidatos registrados.
     *
     * @param pageable información de paginación como número de página, tamaño y orden
     * @return respuesta con la lista paginada de candidatos
     */
    @GetMapping
    public ResponseEntity<PagedResponse<CandidatoResponseDTO>> getAll(Pageable pageable) {
        return ResponseEntity.ok(candidatoService.getAllCandidatos(pageable));
    }

    /**
     * Obtiene los detalles de un candidato específico por su ID.
     *
     * @param id identificador único del candidato
     * @return DTO con los datos del candidato correspondiente
     */
    @GetMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(candidatoService.getCandidatoById(id));
    }

    /**
     * Crea un nuevo candidato en el sistema.
     *
     * @param dto datos del candidato a registrar
     * @return DTO con los datos del candidato creado
     */
    @PostMapping
    public ResponseEntity<CandidatoResponseDTO> create(@Valid @RequestBody CandidatoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(candidatoService.saveCandidato(dto));
    }

    /**
     * Actualiza completamente los datos de un candidato existente por su ID.
     *
     * @param id  identificador del candidato a actualizar
     * @param dto DTO con los datos actualizados
     * @return DTO con los datos del candidato actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody CandidatoUpdateDTO dto) {
        return ResponseEntity.ok(candidatoService.updateCandidato(id, dto));
    }

    /**
     * Realiza una actualización parcial sobre los datos de un candidato.
     *
     * @param id  identificador del candidato
     * @param dto DTO con los campos a modificar
     * @return DTO con los datos actualizados parcialmente
     */
    @PatchMapping("/{id}")
    public ResponseEntity<CandidatoResponseDTO> patch(
            @PathVariable Long id,
            @Valid @RequestBody CandidatoPatchDTO dto) {
        return ResponseEntity.ok(candidatoService.actualizarParcial(id, dto));
    }

    /**
     * Elimina un candidato del sistema por su ID.
     *
     * @param id identificador del candidato a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        candidatoService.deleteCandidato(id);
    }

    /**
     * Inicia una exportación asíncrona de candidatos en el formato especificado.
     * <p>
     * Si {@code exportAll} es {@code true}, se exportan todos los registros disponibles.
     * En caso contrario, se exporta únicamente la página solicitada.
     * </p>
     *
     * @param requestDTO DTO con los parámetros de exportación, incluyendo formato, paginación y tipo de exportación
     * @return respuesta con estado 202 Accepted y el ID de la exportación iniciada
     */
    @PostMapping("/export/async")
    public CompletableFuture<ResponseEntity<String>> exportAsync(
            @Valid @RequestBody ExportRequestDTO requestDTO) {

        Pageable pageable = requestDTO.isExportAll()
                ? Pageable.unpaged()
                : PageRequest.of(requestDTO.getPage(), requestDTO.getSize());

        return candidatoService.exportarCandidatosAsync(requestDTO.getFormat(), pageable)
                .thenApply(id -> ResponseEntity.accepted()
                        .body("Exportación en proceso. ID: " + id));
    }

    /**
     * Devuelve el resultado de una exportación asíncrona previamente iniciada.
     * <p>
     * Si la exportación aún no ha sido completada o no existe, devuelve un mensaje informativo.
     * De lo contrario, retorna el archivo exportado como adjunto.
     * </p>
     *
     * @param exportId identificador único de la exportación
     * @return archivo exportado o mensaje de estado
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
