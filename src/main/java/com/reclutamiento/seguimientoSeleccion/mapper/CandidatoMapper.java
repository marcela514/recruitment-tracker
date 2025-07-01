package com.reclutamiento.seguimientoSeleccion.mapper;

import com.reclutamiento.seguimientoSeleccion.dto.*;
import com.reclutamiento.seguimientoSeleccion.model.Candidato;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import static com.reclutamiento.seguimientoSeleccion.util.EnumLabelUtil.getLabel;
import java.time.format.DateTimeFormatter;

/**
 * Mapper para transformar entre entidades {@link Candidato} y sus distintos DTOs
 * utilizando MapStruct. También se encarga de completar campos derivados como los labels
 * internacionalizados.
 */
@Mapper(componentModel = "spring")
public abstract class CandidatoMapper {

    /**
     * Fuente de mensajes para internacionalización de etiquetas (labels).
     */
    @Autowired
    protected MessageSource messageSource;

    /**
     * Convierte un DTO de creación en una entidad {@link Candidato}.
     *
     * @param dto DTO con los datos de creación del candidato.
     * @return Entidad {@link Candidato} correspondiente.
     */
    public abstract Candidato toEntity(CandidatoCreateDTO dto);

    /**
     * Convierte una entidad {@link Candidato} a un DTO de respuesta.
     *
     * @param candidato Entidad del candidato.
     * @return DTO con la información del candidato para respuestas.
     */
    public abstract CandidatoResponseDTO toDTO(Candidato candidato);

    /**
     * Actualiza una entidad {@link Candidato} existente con los valores de un DTO de actualización.
     *
     * @param dto    DTO con los nuevos valores.
     * @param entity Entidad a actualizar.
     */
    public abstract void updateEntityFromDto(CandidatoUpdateDTO dto, @MappingTarget Candidato entity);

    /**
     * Actualiza parcialmente una entidad {@link Candidato} con un DTO de tipo PATCH,
     * ignorando los campos nulos.
     *
     * @param dto    DTO con los campos a actualizar.
     * @param entity Entidad a actualizar.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void patchEntityFromDto(CandidatoPatchDTO dto, @MappingTarget Candidato entity);

    /**
     * Método ejecutado después del mapeo principal para completar los campos derivados (labels)
     * de los enums utilizando la internacionalización.
     *
     * @param candidato Entidad original.
     * @param dto       DTO resultante donde se asignan los labels.
     */
    @AfterMapping
    protected void fillLabels(Candidato candidato, @MappingTarget CandidatoResponseDTO dto) {
        if (candidato == null || dto == null) return;

        var locale = LocaleContextHolder.getLocale();

        if (candidato.getEstado() != null) {
            dto.setEstadoLabel(getLabel(candidato.getEstado(), messageSource, locale));
            dto.setEstado(candidato.getEstado().name());
        }

        if (candidato.getGenero() != null) {
            dto.setGeneroLabel(getLabel(candidato.getGenero(), messageSource, locale));
            dto.setGenero(candidato.getGenero().name());
        }

        if (candidato.getTipoDocumento() != null) {
            dto.setTipoDocumentoLabel(getLabel(candidato.getTipoDocumento(), messageSource, locale));
            dto.setTipoDocumento(candidato.getTipoDocumento().name());
        }

        if (candidato.getNivelEducativo() != null) {
            dto.setNivelEducativoLabel(getLabel(candidato.getNivelEducativo(), messageSource, locale));
            dto.setNivelEducativo(candidato.getNivelEducativo().name());
        }

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (candidato.getCreatedBy() != null) {
            dto.setCreadoPor(candidato.getCreatedBy());
        }

        if (candidato.getCreatedAt() != null) {
            dto.setFechaCreacion(dateTimeFormatter.format(candidato.getCreatedAt()));
        }

        if (candidato.getModifiedBy() != null) {
            dto.setModificadoPor(candidato.getModifiedBy());
        }

        if (candidato.getUpdatedAt() != null) {
            dto.setFechaModificacion(dateTimeFormatter.format(candidato.getUpdatedAt()));
        }

    }

    /**
     * Obtiene el mensaje localizado para una clave dada, o devuelve la clave si no se encuentra.
     *
     * @param key    Clave del mensaje.
     * @param locale Localización actual.
     * @return Mensaje localizado o la clave si no hay traducción.
     */
    private String getMessage(String key, java.util.Locale locale) {
        return messageSource.getMessage(key, null, key, locale);
    }
}
