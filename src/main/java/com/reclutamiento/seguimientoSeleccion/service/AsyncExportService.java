package com.reclutamiento.seguimientoSeleccion.service;

import com.reclutamiento.seguimientoSeleccion.config.ExportLimitsProperties;
import com.reclutamiento.seguimientoSeleccion.dto.ExportResult;
import com.reclutamiento.seguimientoSeleccion.exception.ExportLimitExceededException;
import com.reclutamiento.seguimientoSeleccion.export.ExportDataProvider;
import com.reclutamiento.seguimientoSeleccion.export.ExportStrategy;
import com.reclutamiento.seguimientoSeleccion.export.ExportStrategyFactory;
import com.reclutamiento.seguimientoSeleccion.export.PaginatedExportDataProvider;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * Servicio encargado de manejar la exportación asincrónica de datos.
 * <p>
 * Proporciona una forma genérica para exportar datos paginados en segundo plano,
 * soportando múltiples formatos (por ejemplo, PDF, Excel, CSV).
 * Los resultados de exportación se almacenan temporalmente en memoria y se acceden mediante un ID generado.
 */
@Service
public class AsyncExportService {

    /**
     * Mapa concurrente para almacenar resultados de exportaciones en memoria,
     * indexados por un ID único.
     */
    private final ConcurrentHashMap<String, ExportResult> exportResults = new ConcurrentHashMap<>();

    /**
     * Propiedades que contienen los límites máximos permitidos para cada formato de exportación.
     */
    private final ExportLimitsProperties limits;

    /**
     * Constructor que inyecta las propiedades de límites de exportación.
     *
     * @param limits propiedades de límites configurados para exportaciones
     */
    public AsyncExportService(ExportLimitsProperties limits) {
        this.limits = limits;
    }

    /**
     * Obtiene el resultado de una exportación almacenada en memoria según el ID proporcionado.
     *
     * @param exportId ID único de la exportación
     * @return resultado de la exportación, o null si no existe
     */
    public ExportResult getExportResult(String exportId) {
        return exportResults.get(exportId);
    }

    /**
     * Ejecuta una exportación asincrónica de datos paginados en el formato especificado.
     * <p>
     * Valida que la cantidad total de registros no exceda el límite configurado para el formato.
     * Si se excede, lanza una {@link ExportLimitExceededException}.
     * En caso de errores inesperados, la excepción se propaga para ser manejada por el controlador global.
     *
     * @param format         formato de exportación deseado (ej. "pdf", "excel", "csv")
     * @param pageFetcher    función para obtener páginas de datos según el Pageable
     * @param headers        lista de nombres de columnas para la exportación
     * @param valueExtractors lista de funciones que extraen valores de cada objeto para las columnas
     * @param <T>            tipo genérico de datos a exportar
     * @return un CompletableFuture con el ID de la exportación si se realiza correctamente,
     *         o uno completado excepcionalmente si ocurre un error
     */
    @Async
    public <T> CompletableFuture<String> exportAsync(
            String format,
            Function<Pageable, Page<T>> pageFetcher,
            List<String> headers,
            List<Function<T, Object>> valueExtractors
    ) {
        try {
            // Construcción del proveedor que paginará los datos para la exportación
            ExportDataProvider<T> provider = new PaginatedExportDataProvider<>(pageFetcher, headers, valueExtractors);
            int totalCount = provider.getTotalCount();

            // Determinar el límite máximo permitido para el formato solicitado
            int maxAllowed = switch (format.toLowerCase()) {
                case "pdf" -> limits.getPdf();
                case "excel" -> limits.getExcel();
                case "csv" -> limits.getCsv();
                default -> Integer.MAX_VALUE;
            };

            // Validar límite y lanzar excepción personalizada si se excede
            if (totalCount > maxAllowed) {
                throw new ExportLimitExceededException(format, maxAllowed, totalCount);
            }

            // Obtener la estrategia de exportación adecuada según el formato
            ExportStrategy<T> strategy = ExportStrategyFactory.getStrategy(format);
            byte[] data = strategy.export(provider);

            // Generar un ID único para esta exportación y almacenarla
            String exportId = UUID.randomUUID().toString();
            exportResults.put(exportId, new ExportResult(data, "exported-data." + strategy.getFileExtension(), strategy.getContentType()));

            // Devolver un CompletableFuture completado exitosamente con el ID generado
            return CompletableFuture.completedFuture(exportId);

        } catch (Exception e) {
            // Completar excepcionalmente el futuro con la excepción original para que
            // sea manejada en el GlobalExceptionHandler o en el controlador
            CompletableFuture<String> failedFuture = new CompletableFuture<>();
            failedFuture.completeExceptionally(e);
            return failedFuture;
        }
    }
}
