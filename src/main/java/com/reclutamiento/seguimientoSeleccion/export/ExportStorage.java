package com.reclutamiento.seguimientoSeleccion.export;

import com.reclutamiento.seguimientoSeleccion.dto.ExportResult;

public interface ExportStorage {
    void save(String id, ExportResult result);
    ExportResult get(String id);
}
