package com.reclutamiento.seguimientoSeleccion.mapper;

import com.reclutamiento.seguimientoSeleccion.dto.*;
import com.reclutamiento.seguimientoSeleccion.model.Candidato;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CandidatoMapper {

    // Convertir CreateDTO a entidad
    Candidato toEntity(CandidatoCreateDTO dto);

    // Convertir entidad a ResponseDTO
    CandidatoResponseDTO toDTO(Candidato candidato);

    // Actualizar entidad desde UpdateDTO (PUT)
    void updateEntityFromDto(CandidatoUpdateDTO dto, @MappingTarget Candidato entity);

    // Actualizar parcialmente entidad desde PatchDTO (PATCH) ignorando campos nulos
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchEntityFromDto(CandidatoPatchDTO dto, @MappingTarget Candidato entity);

    // Después del mapeo, llenar los campos label para el DTO respuesta
    @AfterMapping
    default void fillLabels(Candidato candidato, @MappingTarget CandidatoResponseDTO dto) {
        if (candidato == null || dto == null) return;

        if (candidato.getEstado() != null) {
            dto.setEstadoLabel(candidato.getEstado().getLabel());
            dto.setEstado(candidato.getEstado().name());
        }

        if (candidato.getGenero() != null) {
            dto.setGeneroLabel(candidato.getGenero().getLabel());
            dto.setGenero(candidato.getGenero().name());
        }

        if (candidato.getTipoDocumento() != null) {
            dto.setTipoDocumentoLabel(candidato.getTipoDocumento().getLabel());
            dto.setTipoDocumento(candidato.getTipoDocumento().name());
        }

        if (candidato.getNivelEducativo() != null) {
            dto.setNivelEducativoLabel(candidato.getNivelEducativo().getLabel());
            dto.setNivelEducativo(candidato.getNivelEducativo().name());
        }
    }
}
