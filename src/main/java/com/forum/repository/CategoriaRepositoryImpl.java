package com.forum.repository;

import com.forum.model.Categoria;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoriaRepositoryImpl implements CategoriaRepository {
    private static final String FILE_PATH = "src/main/resources/data/categorias.json";
    private final Gson gson = new Gson();

    @Override
    public void save(Categoria categoria) {
        List<Categoria> categorias = findAll();
        categorias.add(categoria);
        writeToFile(categorias);
    }

    @Override
    public void update(Categoria categoria) {
        List<Categoria> categorias = findAll();
        categorias.replaceAll(c -> c.getId().equals(categoria.getId()) ? categoria : c);
        writeToFile(categorias);
    }

    @Override
    public void delete(String id) {
        List<Categoria> categorias = findAll();
        categorias.removeIf(c -> c.getId().equals(id));
        writeToFile(categorias);
    }

    @Override
    public List<Categoria> findAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Categoria>>(){}.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Categoria> findById(String id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<Categoria> findByNombre(String nombre) {
        return findAll().stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .findFirst();
    }
    @Override
    public boolean existsById(String id) { // ✅ Implementación
        return findAll().stream()
                .anyMatch(c -> c.getId().equals(id));
    }
    private void writeToFile(List<Categoria> categorias) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(categorias, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar categorías", e);
        }
    }
}