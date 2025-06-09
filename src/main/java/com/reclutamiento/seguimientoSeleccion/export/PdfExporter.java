package com.reclutamiento.seguimientoSeleccion.export;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.AreaBreakType;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

public class PdfExporter<T> implements ExportStrategy<T> {

    // Cantidad máxima de filas de datos por página (sin contar encabezados)
    private static final int ROWS_PER_PAGE = 40;

    @Override
    public byte[] export(ExportDataProvider<T> provider) throws Exception {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            document.setFont(font);

            List<String> headers = provider.getHeaders();
            List<Function<T, Object>> valueExtractors = provider.getValueExtractors();

            int totalCount = provider.getTotalCount();
            int offset = 0;

            while (offset < totalCount) {
                int limit = Math.min(ROWS_PER_PAGE, totalCount - offset);
                List<T> dataChunk = provider.getData(offset, limit);

                Table table = new Table(headers.size());

                // Agregar encabezados
                for (String col : headers) {
                    table.addHeaderCell(new Cell().add(new Paragraph(col).setBold()));
                }

                // Agregar filas de datos
                for (T item : dataChunk) {
                    for (Function<T, Object> extractor : valueExtractors) {
                        Object value = extractor.apply(item);
                        String text = formatValue(value);
                        table.addCell(new Cell().add(new Paragraph(text)));
                    }
                }

                document.add(table);

                offset += limit;

                // Si no es la última página, insertar salto de página
                if (offset < totalCount) {
                    document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
                }
            }

            document.close();

            return baos.toByteArray();
        }
    }

    private String formatValue(Object value) {
        if (value == null) return "";
        if (value instanceof LocalDate) return ((LocalDate) value).toString();
        if (value instanceof LocalDateTime) return ((LocalDateTime) value).toString();
        return value.toString();
    }

    @Override
    public String getFileExtension() {
        return "pdf";
    }

    @Override
    public String getContentType() {
        return "application/pdf";
    }
}
