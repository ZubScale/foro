package com.forum.repository;

import com.forum.model.Tag;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagRepositoryImpl implements TagRepository {
    private static final String FILE_PATH = "src/main/resources/data/tags.json";
    private final Gson gson = new Gson();

    @Override
    public void save(Tag tag) {
        List<Tag> tags = findAll();
        tags.add(tag);
        writeToFile(tags);
    }

    @Override
    public void update(Tag tag) {
        List<Tag> tags = findAll();
        tags.replaceAll(t -> t.getId().equals(tag.getId()) ? tag : t);
        writeToFile(tags);
    }

    @Override
    public void delete(String id) {
        List<Tag> tags = findAll();
        tags.removeIf(t -> t.getId().equals(id));
        writeToFile(tags);
    }

    @Override
    public List<Tag> findAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Tag>>(){}.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Tag> findById(String id) {
        return findAll().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Tag> findByNombre(String nombre) {
        return findAll().stream()
                .filter(t -> t.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }

    private void writeToFile(List<Tag> tags) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tags, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en tags.json", e);
        }
    }
}