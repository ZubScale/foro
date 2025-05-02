package com.forum.repository;

import com.forum.model.Comunidad;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComunidadRepositoryImpl implements ComunidadRepository {
    private static final String FILE_PATH = "src/main/resources/data/comunidades.json";
    private final Gson gson = new Gson();

    @Override
    public void save(Comunidad comunidad) {
        List<Comunidad> comunidades = findAll();
        comunidades.add(comunidad);
        writeToFile(comunidades);
    }

    @Override
    public void update(Comunidad comunidad) {
        List<Comunidad> comunidades = findAll();
        comunidades.replaceAll(c -> c.getId().equals(comunidad.getId()) ? comunidad : c);
        writeToFile(comunidades);
    }

    @Override
    public void delete(String id) {
        List<Comunidad> comunidades = findAll();
        comunidades.removeIf(c -> c.getId().equals(id));
        writeToFile(comunidades);
    }

    @Override
    public List<Comunidad> findAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Comunidad>>(){}.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Comunidad> findById(String id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(String id) {
        return false;
    }

    private void writeToFile(List<Comunidad> comunidades) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(comunidades, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar comunidades", e);
        }
    }
}