package com.forum.repository;

import com.forum.model.TipoCalificacion;
import com.forum.repository.TipoCalificacionRepository;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TipoCalificacionRepositoryImpl implements TipoCalificacionRepository {
    private static final String FILE_PATH = "data/tipos_calificaciones.json"; // Ruta relativa
    private final Gson gson = new Gson();

    // Implementación del método delete faltante
    @Override
    public void delete(String id) {
        List<TipoCalificacion> tipos = findAll();
        List<TipoCalificacion> tiposActualizados = tipos.stream()
                .filter(t -> !t.getId().equals(id))
                .collect(Collectors.toList());
        writeToFile(tiposActualizados);
    }

    // Métodos existentes mejorados
    @Override
    public void save(TipoCalificacion tipo) {
        List<TipoCalificacion> tipos = findAll();
        tipos.add(tipo);
        writeToFile(tipos);
    }

    @Override
    public void update(TipoCalificacion tipo) {
        List<TipoCalificacion> tipos = findAll().stream()
                .map(t -> t.getId().equals(tipo.getId()) ? tipo : t)
                .collect(Collectors.toList());
        writeToFile(tipos);
    }

    @Override
    public List<TipoCalificacion> findAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            List<TipoCalificacion> tipos = gson.fromJson(reader, new TypeToken<ArrayList<TipoCalificacion>>(){}.getType());
            return tipos != null ? tipos : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<TipoCalificacion> findById(String id) {
        return findAll().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<TipoCalificacion> findByDescripcion(String descripcion) {
        return findAll().stream()
                .filter(t -> t.getDescripcion().equalsIgnoreCase(descripcion))
                .findFirst();
    }

    private void writeToFile(List<TipoCalificacion> tipos) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tipos, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar tipos de calificación: " + e.getMessage(), e);
        }
    }
}