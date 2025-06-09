package com.reclutamiento.seguimientoSeleccion.service;

import com.reclutamiento.seguimientoSeleccion.dto.*;
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
 * Proporciona operaciones para consulta, creación, actualización (completa y parcial),
 * eliminación y exportación asincrónica.
 */
@Service
@RequiredArgsConstructor
public class CandidatoService {

    private final CandidatoRepository candidatoRepository;
    private final AsyncExportService asyncExportService;
    private final CandidatoMapper candidatoMapper;

    public PagedResponse<CandidatoResponseDTO> getAllCandidatos(Pageable pageable) {
        Page<Candidato> page = candidatoRepository.findAll(pageable);
        Page<CandidatoResponseDTO> dtoPage = page.map(candidatoMapper::toDTO);
        return PagedResponse.fromPage(dtoPage);
    }

    public CandidatoResponseDTO getCandidatoById(Long id) {
        return candidatoRepository.findById(id)
                .map(candidatoMapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Candidato", "id", id));
    }

    public CandidatoResponseDTO saveCandidato(CandidatoCreateDTO dto) {
        Candidato candidato = candidatoMapper.toEntity(dto);
        Candidato saved = candidatoRepository.save(candidato);
        return candidatoMapper.toDTO(saved);
    }

    public void deleteCandidato(Long id) {
        if (!candidatoRepository.existsById(id)) {
            throw new NotFoundException("Candidato con ID " + id + " no encontrado");
        }
        candidatoRepository.deleteById(id);
    }

    public CandidatoResponseDTO updateCandidato(Long id, CandidatoUpdateDTO dto) {
        Candidato candidatoExistente = candidatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidato", "id", id));

        candidatoMapper.updateEntityFromDto(dto, candidatoExistente);

        Candidato saved = candidatoRepository.save(candidatoExistente);
        return candidatoMapper.toDTO(saved);
    }

    public CandidatoResponseDTO actualizarParcial(Long id, CandidatoPatchDTO patchDTO) {
        Candidato candidatoOriginal = candidatoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Candidato", "id", id));

        candidatoMapper.patchEntityFromDto(patchDTO, candidatoOriginal);

        Candidato candidatoActualizado = candidatoRepository.save(candidatoOriginal);

        return candidatoMapper.toDTO(candidatoActualizado);
    }

    public List<CandidatoResponseDTO> getAllCandidatos() {
        List<Candidato> candidatos = candidatoRepository.findAll();
        return candidatos.stream()
                .map(candidatoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CompletableFuture<String> exportarCandidatosAsync(String format, Pageable pageable) {
        return asyncExportService.exportAsync(
                format,
                page -> candidatoRepository.findAll(page).map(candidatoMapper::toDTO),
                List.of("ID", "Nombre", "Correo", "Teléfono", "Estado", "Fecha Registro"),
                List.of(
                        CandidatoResponseDTO::getId,
                        CandidatoResponseDTO::getNombre,
                        CandidatoResponseDTO::getEmail,
                        CandidatoResponseDTO::getTelefono,
                        CandidatoResponseDTO::getEstado,
                        CandidatoResponseDTO::getFechaRegistro
                )
        );
    }
}
