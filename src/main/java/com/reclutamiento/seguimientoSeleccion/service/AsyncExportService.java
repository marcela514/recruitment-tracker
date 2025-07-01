package com.reclutamiento.seguimientoSeleccion.service;

import com.reclutamiento.seguimientoSeleccion.config.ExportLimitsProperties;
import com.reclutamiento.seguimientoSeleccion.dto.ExportResult;
import com.reclutamiento.seguimientoSeleccion.enums.ExportFormat;
import com.reclutamiento.seguimientoSeleccion.exception.ExportLimitExceededException;
import com.reclutamiento.seguimientoSeleccion.export.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Servicio encargado de manejar la exportación asincrónica de datos.
 * <p>
 * Permite exportar listas o páginas de datos en segundo plano en múltiples formatos (PDF, Excel, CSV),
 * almacenando el resultado temporalmente en memoria. Cada exportación es identificada mediante un ID único.
 * </p>
 * <p>
 * Soporta control de límites configurables por formato y expiración automática del resultado exportado.
 * </p>
 *
 * @see com.reclutamiento.seguimientoSeleccion.export.ExportStrategy
 * @see com.reclutamiento.seguimientoSeleccion.dto.ExportResult
 * @see com.reclutamiento.seguimientoSeleccion.config.ExportLimitsProperties
 */
@Service
public class AsyncExportService {

    /**
     * Mapa concurrente que almacena los resultados de exportaciones en memoria,
     * usando como clave un ID único.
     */
    private final ConcurrentHashMap<String, ExportResult> exportResults = new ConcurrentHashMap<>();

    /**
     * Programador de tareas para limpiar exportaciones expiradas.
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    /**
     * Propiedades de configuración que definen los límites de exportación
     * por formato y el tiempo de expiración en minutos.
     */
    private final ExportLimitsProperties limits;

    /**
     * Constructor que inyecta las propiedades de límites de exportación.
     *
     * @param limits propiedades que definen el número máximo de registros y tiempo de expiración por formato
     */
    public AsyncExportService(ExportLimitsProperties limits) {
        this.limits = limits;
    }

    /**
     * Obtiene el resultado de una exportación previamente almacenada.
     *
     * @param exportId identificador único de la exportación
     * @return el resultado de la exportación o {@code null} si no existe o ha expirado
     */
    public ExportResult getExportResult(String exportId) {
        return exportResults.get(exportId);
    }

    /**
     * Ejecuta una exportación asincrónica para una lista completa de datos.
     *
     * @param format     formato de exportación (pdf, excel, csv)
     * @param headers    encabezados de columna
     * @param extractors funciones para extraer los valores de cada campo del objeto
     * @param data       lista completa de datos a exportar
     * @param <T>        tipo de dato a exportar
     * @return un {@link CompletableFuture} que contiene el ID de la exportación
     * @throws ExportLimitExceededException si se excede el límite configurado
     */
    @Async
    public <T> CompletableFuture<String> exportAll(
            ExportFormat format,
            List<String> headers,
            List<Function<T, Object>> extractors,
            List<T> data
    ) {
        try {
            int totalCount = data.size();

            int maxAllowed = limits.getLimitByFormat(format);
            if (totalCount > maxAllowed) {
                throw new ExportLimitExceededException(format, maxAllowed, totalCount);
            }

            ExportDataProvider<T> provider = new ListExportDataProvider<>(data, headers, extractors);
            ExportStrategy<T> strategy = ExportStrategyFactory.getStrategy(format);
            byte[] exportData = strategy.export(provider);

            String exportId = UUID.randomUUID().toString();
            exportResults.put(exportId, new ExportResult(
                    exportData,
                    "exported-data." + strategy.getFileExtension(),
                    strategy.getContentType()
            ));
            scheduleExportExpiration(exportId, limits.getExpirationMinutes());

            return CompletableFuture.completedFuture(exportId);
        } catch (Exception e) {
            CompletableFuture<String> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }
    }

    /**
     * Ejecuta una exportación asincrónica de datos usando paginación.
     * <p>
     * Ideal para grandes volúmenes de datos que se deben cargar por bloques desde la base de datos.
     * </p>
     *
     * @param format      formato de exportación
     * @param headers     encabezados de columna
     * @param extractors  funciones para extraer los campos del DTO
     * @param pageable    configuración de paginación (puede ser ignorada si el pageFetcher la sobreescribe)
     * @param pageFetcher función que devuelve una página de datos a partir de un {@link Pageable}
     * @param <T>         tipo de dato exportado
     * @return un {@link CompletableFuture} con el ID de la exportación
     * @throws ExportLimitExceededException si se excede el número máximo permitido
     */
    @Async
    public <T> CompletableFuture<String> exportPaged(
            ExportFormat format,
            List<String> headers,
            List<Function<T, Object>> extractors,
            Pageable pageable,
            Function<Pageable, Page<T>> pageFetcher
    ) {
        try {
            ExportDataProvider<T> provider = new PaginatedExportDataProvider<>(pageFetcher, headers, extractors);
            int totalCount = provider.getTotalCount();

            int maxAllowed = limits.getLimitByFormat(format);
            if (totalCount > maxAllowed) {
                throw new ExportLimitExceededException(format, maxAllowed, totalCount);
            }

            ExportStrategy<T> strategy = ExportStrategyFactory.getStrategy(format);
            byte[] exportData = strategy.export(provider);

            String exportId = UUID.randomUUID().toString();
            exportResults.put(exportId, new ExportResult(
                    exportData,
                    "exported-data." + strategy.getFileExtension(),
                    strategy.getContentType()
            ));
            scheduleExportExpiration(exportId, limits.getExpirationMinutes());

            return CompletableFuture.completedFuture(exportId);
        } catch (Exception e) {
            CompletableFuture<String> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }
    }

    /**
     * Programa la eliminación automática del resultado de exportación después de un período de tiempo.
     *
     * @param exportId ID del resultado exportado
     * @param minutes  tiempo en minutos tras el cual se elimina el resultado
     */
    private void scheduleExportExpiration(String exportId, int minutes) {
        scheduler.schedule(() -> exportResults.remove(exportId), minutes, TimeUnit.MINUTES);
    }
}
