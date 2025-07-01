package com.reclutamiento.seguimientoSeleccion.service;

import com.reclutamiento.seguimientoSeleccion.dto.*;
import com.reclutamiento.seguimientoSeleccion.enums.ExportFormat;
import com.reclutamiento.seguimientoSeleccion.exception.NotFoundException;
import com.reclutamiento.seguimientoSeleccion.mapper.CandidatoMapper;
import com.reclutamiento.seguimientoSeleccion.model.Candidato;
import com.reclutamiento.seguimientoSeleccion.repository.CandidatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Servicio encargado de gestionar la lógica de negocio relacionada con los candidatos.
 * <p>
 * Proporciona operaciones para consultar, crear, actualizar (completa y parcialmente),
 * eliminar candidatos y exportar sus datos de forma asíncrona.
 * </p>
 *
 * @see com.reclutamiento.seguimientoSeleccion.repository.CandidatoRepository
 * @see com.reclutamiento.seguimientoSeleccion.mapper.CandidatoMapper
 * @see com.reclutamiento.seguimientoSeleccion.service.AsyncExportService
 */
@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final AsyncExportService asyncExportService;
    private final CandidatoMapper candidatoMapper;

    /**
     * Obtiene todos los candidatos paginados.
     *
     * @param pageable configuración de paginación
     * @return respuesta paginada con los candidatos
     */
    public PagedResponse<CandidatoResponseDTO> getAllCandidatos(Pageable pageable) {
        Page<Candidato> page = candidatoRepository.findAll(pageable);
        Page<CandidatoResponseDTO> dtoPage = page.map(candidatoMapper::toDTO);
        return PagedResponse.fromPage(dtoPage);
    }

    /**
     * Busca un candidato por su ID.
     *
     * @param id identificador del candidato
     * @return DTO del candidato encontrado
     * @throws NotFoundException si no se encuentra el candidato
     */
    public CandidatoResponseDTO getCandidatoById(Long id) {
        return candidatoRepository.findById(id)
                .map(candidatoMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Candidato", "id", id));
    }

    /**
     * Registra un nuevo candidato en el sistema.
     *
     * @param dto datos del candidato a crear
     * @return DTO del candidato creado
     */
    public CandidatoResponseDTO saveCandidato(CandidatoCreateDTO dto) {
        Candidato candidato = candidatoMapper.toEntity(dto);
        Candidato saved = candidatoRepository.save(candidato);
        return candidatoMapper.toDTO(saved);
    }

    /**
     * Elimina un candidato por su ID.
     *
     * @param id identificador del candidato a eliminar
     * @throws NotFoundException si el candidato no existe
     */
    public void deleteCandidato(Long id) {
        if (!candidatoRepository.existsById(id)) {
            throw new NotFoundException("Candidato con ID " + id + " no encontrado");
        }
        candidatoRepository.deleteById(id);
    }

    /**
     * Actualiza completamente un candidato existente.
     *
     * @param id  identificador del candidato
     * @param dto datos nuevos para actualizar al candidato
     * @return DTO actualizado del candidato
     * @throws NotFoundException si el candidato no existe
     */
    public CandidatoResponseDTO updateCandidato(Long id, CandidatoUpdateDTO dto) {
        Candidato candidatoExistente = candidatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidato", "id", id));

        candidatoMapper.updateEntityFromDto(dto, candidatoExistente);

        Candidato saved = candidatoRepository.save(candidatoExistente);
        return candidatoMapper.toDTO(saved);
    }

    /**
     * Realiza una actualización parcial de los datos de un candidato.
     *
     * @param id        identificador del candidato
     * @param patchDTO  DTO con los campos a modificar
     * @return DTO actualizado del candidato
     * @throws NotFoundException si el candidato no existe
     */
    public CandidatoResponseDTO actualizarParcial(Long id, CandidatoPatchDTO patchDTO) {
        Candidato candidatoOriginal = candidatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidato", "id", id));

        candidatoMapper.patchEntityFromDto(patchDTO, candidatoOriginal);

        Candidato candidatoActualizado = candidatoRepository.save(candidatoOriginal);

        return candidatoMapper.toDTO(candidatoActualizado);
    }

    /**
     * Obtiene la lista completa de candidatos sin paginación.
     *
     * @return lista de todos los candidatos como DTO
     */
    public List<CandidatoResponseDTO> getAllCandidatos() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        return candidatos.stream()
                .map(candidatoMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Exporta los datos de candidatos de forma asíncrona.
     * <p>
     * Si {@code pageable} está sin paginar, se exportan todos los registros.
     * En caso contrario, se exporta solo la página especificada.
     * </p>
     *
     * @param format   formato de exportación (PDF, EXCEL, CSV)
     * @param pageable configuración de paginación o sin paginar
     * @return ID de la exportación en proceso
     */
    public CompletableFuture<String> exportarCandidatosAsync(ExportFormat format, Pageable pageable) {
        List<String> headers = List.of("ID", "Nombre", "Correo", "Teléfono", "Estado", "Fecha Registro");
        List<java.util.function.Function<CandidatoResponseDTO, Object>> extractors = List.of(
                CandidatoResponseDTO::getId,
                CandidatoResponseDTO::getNombre,
                CandidatoResponseDTO::getEmail,
                CandidatoResponseDTO::getTelefono,
                CandidatoResponseDTO::getEstado,
                CandidatoResponseDTO::getFechaRegistro
        );

        if (pageable.isUnpaged()) {
            List<CandidatoResponseDTO> all = candidatoRepository.findAll()
                    .stream()
                    .map(candidatoMapper::toDTO)
                    .toList();

            return asyncExportService.exportAll(format, headers, extractors, all);
        } else {
            return asyncExportService.exportPaged(format, headers, extractors, pageable,
                    page -> candidatoRepository.findAll(page).map(candidatoMapper::toDTO));
        }
    }
}
