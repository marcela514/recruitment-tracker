package com.reclutamiento.seguimientoSeleccion.export;

import com.reclutamiento.seguimientoSeleccion.dto.ExportResult;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryExportStorage implements ExportStorage {

    private final Map<String, ExportResult> store = new ConcurrentHashMap<>();

    @Override
    public void save(String id, ExportResult result) {
        store.put(id, result);
    }

    @Override
    public ExportResult get(String id) {
        return store.get(id);
    }
}
