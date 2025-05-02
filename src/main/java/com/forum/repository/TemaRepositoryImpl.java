package com.forum.repository;

import com.forum.model.Tema;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TemaRepositoryImpl implements TemaRepository {
    private static final String FILE_PATH = "src/main/resources/data/temas.json";
    private final Gson gson = new Gson();

    @Override
    public void save(Tema tema) {
        List<Tema> temas = findAll();
        temas.add(tema);
        writeToFile(temas);
    }

    @Override
    public void update(Tema tema) {
        List<Tema> temas = findAll();
        temas.replaceAll(t -> t.getId().equals(tema.getId()) ? tema : t);
        writeToFile(temas);
    }

    @Override
    public void delete(String id) {
        List<Tema> temas = findAll();
        temas.removeIf(t -> t.getId().equals(id));
        writeToFile(temas);
    }

    @Override
    public List<Tema> findAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Tema>>(){}.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Tema> findById(String id) {
        return findAll().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    private void writeToFile(List<Tema> temas) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(temas, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en temas.json", e);
        }
    }
}