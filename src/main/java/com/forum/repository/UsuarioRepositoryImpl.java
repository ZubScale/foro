package com.forum.repository;

import com.forum.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryImpl implements UsuarioRepository {
    private static final String FILE_PATH = "src/main/resources/data/usuarios.json";
    private final Gson gson = new Gson();

    @Override
    public void save(Usuario usuario) {
        List<Usuario> usuarios = findAll();
        usuarios.add(usuario);
        writeToFile(usuarios);
    }

    @Override
    public void update(Usuario usuario) {
        List<Usuario> usuarios = findAll();
        usuarios.replaceAll(u -> u.getNombre().equals(usuario.getNombre()) ? usuario : u);
        writeToFile(usuarios);
    }

    @Override
    public void delete(String username) {
        List<Usuario> usuarios = findAll();
        usuarios.removeIf(u -> u.getNombre().equals(username));
        writeToFile(usuarios);
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return findAll().stream()
                .filter(u -> u.getNombre().equals(username))
                .findFirst();
    }

    @Override
    public boolean existsByEmail(String email) {
        return findAll().stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email));
    }

    @Override
    public boolean existePorEmail(String email) {
        return false;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return findAll().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        return findAll().stream()
                .anyMatch(u -> u.getNombre().equalsIgnoreCase(username));
    }

    private List<Usuario> findAll() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            return gson.fromJson(reader, new TypeToken<ArrayList<Usuario>>() {
            }.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<Usuario> usuarios) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en usuarios.json", e);
        }
    }
}