package com.forum.service;

import com.forum.model.Usuario;
import com.forum.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.UUID;
import java.util.regex.Pattern;

public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepo;
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public UsuarioServiceImpl(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        validarPassword(usuario.getPassword());
        validarEmail(usuario.getEmail());
        validarEdad(usuario.getFechaNacimiento());

        if (existeUsername(usuario.getNombre())) {
            throw new IllegalArgumentException("Nombre de usuario ya existe");
        }
        if (existeEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        usuario.setId(UUID.randomUUID().toString()); // Generar ID único
        usuarioRepo.save(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (!usuarioRepo.existsByUsername(usuario.getNombre())) {
            throw new IllegalArgumentException("Usuario no existe");
        }
        usuarioRepo.update(usuario);
    }

    @Override
    public void eliminarUsuario(String username) {
        if (!usuarioRepo.existsByUsername(username)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuarioRepo.delete(username);
    }

    @Override
    public Usuario login(String username, String password) {
        Usuario usuario = usuarioRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!usuario.getPassword().equals(password)) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        return usuario;
    }

    @Override
    public boolean existeUsername(String username) {
        return usuarioRepo.existsByUsername(username);
    }

    @Override
    public boolean existeEmail(String email) {
        return usuarioRepo.existsByEmail(email);
    }

    // Métodos de validación (sin cambios)
    private void validarPassword(String password) { /* ... */ }
    private void validarEmail(String email) { /* ... */ }
    private void validarEdad(LocalDate fechaNacimiento) { /* ... */ }
}