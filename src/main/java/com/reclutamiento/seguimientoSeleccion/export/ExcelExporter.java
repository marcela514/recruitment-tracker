package com.reclutamiento.seguimientoSeleccion.export;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Exportador de datos a archivo Excel (.xlsx) usando Apache POI SXSSFWorkbook para
 * manejo eficiente de grandes volúmenes.
 * Detecta tipos comunes (String, Number, Boolean, LocalDate, LocalDateTime, Date)
 * y aplica formatos apropiados en celdas.
 *
 * @param <T> Tipo de objeto a exportar.
 */
public class ExcelExporter<T> implements ExportStrategy<T> {

    private static final int PAGE_SIZE = 500;
    private static final int MAX_ROWS_PER_SHEET = 50000;

    @Override
    public byte[] export(ExportDataProvider<T> provider) throws Exception {
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100)) { // Mantiene solo 100 filas en memoria
            List<String> headers = provider.getHeaders();
            List<Function<T, Object>> extractors = provider.getValueExtractors();

            int total = provider.getTotalCount();
            int offset = 0;

            Sheet sheet = null;
            int currentRowNum = 0;
            int sheetIndex = 1;

            // Crear estilos reutilizables para encabezados y fechas
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle dateStyle = createDateCellStyle(workbook);
            CellStyle dateTimeStyle = createDateTimeCellStyle(workbook);

            while (offset < total) {
                if (sheet == null || currentRowNum >= MAX_ROWS_PER_SHEET) {
                    sheet = workbook.createSheet("Datos_" + sheetIndex++);
                    currentRowNum = 0;
                    createHeaderRow(sheet, headers, headerStyle);
                    currentRowNum++;
                }

                int rowsLeftInSheet = MAX_ROWS_PER_SHEET - currentRowNum;
                int limit = Math.min(PAGE_SIZE, Math.min(rowsLeftInSheet, total - offset));
                List<T> pageData = provider.getData(offset, limit);

                for (T item : pageData) {
                    Row row = sheet.createRow(currentRowNum++);
                    for (int i = 0; i < extractors.size(); i++) {
                        Object value = extractors.get(i).apply(item);
                        Cell cell = row.createCell(i);
                        setCellValue(cell, value, dateStyle, dateTimeStyle);
                    }
                }

                offset += limit;
            }

            // Auto-ajustar columnas en todas las hojas (cuidado con hojas muy grandes)
            for (int i = 0; i < sheetIndex - 1; i++) {
                Sheet s = workbook.getSheetAt(i);
                for (int col = 0; col < headers.size(); col++) {
                    s.autoSizeColumn(col);
                }
            }

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                workbook.write(baos);
                workbook.dispose();
                return baos.toByteArray();
            }
        }
    }

    private void createHeaderRow(Sheet sheet, List<String> headers, CellStyle style) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(style);
        }
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

    private CellStyle createDateCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        style.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd"));
        return style;
    }

    private CellStyle createDateTimeCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        style.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-mm-dd HH:mm:ss"));
        return style;
    }

    /**
     * Asigna el valor a la celda según el tipo detectado.
     * Soporta String, Number, Boolean, LocalDate, LocalDateTime, Date.
     * Si el tipo no es reconocido, pone texto usando toString().
     *
     * @param cell          Celda a asignar valor.
     * @param value         Valor a asignar.
     * @param dateStyle     Estilo para fechas sin hora.
     * @param dateTimeStyle Estilo para fechas con hora.
     */
    private void setCellValue(Cell cell, Object value, CellStyle dateStyle, CellStyle dateTimeStyle) {
        if (value == null) {
            cell.setBlank();
            return;
        }
        if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof LocalDate) {
            LocalDate ld = (LocalDate) value;
            Date date = Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
            cell.setCellValue(date);
            cell.setCellStyle(dateStyle);
        } else if (value instanceof LocalDateTime) {
            LocalDateTime ldt = (LocalDateTime) value;
            Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
            cell.setCellValue(date);
            cell.setCellStyle(dateTimeStyle);
        } else if (value instanceof Date) {
            cell.setCellValue((Date) value);
            // No sabemos si incluye hora, así que usamos dateTimeStyle por seguridad
            cell.setCellStyle(dateTimeStyle);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    @Override
    public String getFileExtension() {
        return "xlsx";
    }

    @Override
    public String getContentType() {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    }
}
