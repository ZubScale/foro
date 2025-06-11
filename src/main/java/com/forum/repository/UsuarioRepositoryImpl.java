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
    private final Object lock = new Object();

    @Override
    public void save(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser null");
        synchronized (lock) {
            List<Usuario> usuarios = findAll();
            usuarios.add(usuario);
            writeToFile(usuarios);
        }
    }

    @Override
    public void update(Usuario usuario) {
        if (usuario == null) throw new IllegalArgumentException("Usuario no puede ser null");
        synchronized (lock) {
            List<Usuario> usuarios = findAll();
            usuarios.replaceAll(u -> u.getUsername() != null && u.getUsername().equals(usuario.getUsername()) ? usuario : u);
            writeToFile(usuarios);
        }
    }

    @Override
    public void delete(String username) {
        if (username == null) return;
        synchronized (lock) {
            List<Usuario> usuarios = findAll();
            usuarios.removeIf(u -> u.getUsername() != null && u.getUsername().equals(username));
            writeToFile(usuarios);
        }
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        if (username == null) return Optional.empty();
        return findAll().stream()
                .filter(u -> u.getUsername() != null && u.getUsername().equalsIgnoreCase(username))
                .findFirst();
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        if (email == null) return Optional.empty();
        return findAll().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public boolean existsByUsername(String username) {
        if (username == null) return false;
        return findAll().stream()
                .anyMatch(u -> u.getUsername() != null && u.getUsername().equalsIgnoreCase(username));
    }

    @Override
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return findAll().stream()
                .anyMatch(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email));
    }

    // Alias opcional, si quieres mantenerlo
    @Override
    public boolean existePorEmail(String email) {
        return existsByEmail(email);
    }

    private List<Usuario> findAll() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                writeToFile(new ArrayList<>());
                return new ArrayList<>();
            }

            try (Reader reader = new FileReader(FILE_PATH)) {
                List<Usuario> usuarios = gson.fromJson(reader, new TypeToken<ArrayList<Usuario>>() {}.getType());
                return usuarios != null ? usuarios : new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void writeToFile(List<Usuario> usuarios) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            try (Writer writer = new FileWriter(FILE_PATH)) {
                gson.toJson(usuarios, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al escribir en usuarios.json", e);
        }
    }
}
