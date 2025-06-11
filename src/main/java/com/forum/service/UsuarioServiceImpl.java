package com.forum.service;

import com.forum.model.Usuario;
import com.forum.repository.UsuarioRepository;
import com.forum.util.EmailSender;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;
import java.util.regex.Pattern;

public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepo;
    private static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final int EDAD_MINIMA = 13;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        validarRegistro(usuario);

        usuario.setId(UUID.randomUUID().toString());
        usuarioRepo.save(usuario);

        // Enviar email de bienvenida
        EmailSender.enviarBienvenida(usuario.getEmail(), usuario.getUsername());
    }

    @Override
    public void registrar(Usuario usuario) {
        registrarUsuario(usuario);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (!usuarioRepo.existsByUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("Usuario no existe");
        }
        usuarioRepo.update(usuario);
    }

    @Override
    public void actualizarPassword(String email, String nuevaPassword) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        validarPassword(nuevaPassword);
        usuario.setPassword(nuevaPassword);
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

    private void validarRegistro(Usuario usuario) {
        // Validar username
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }

        if (usuario.getUsername().length() < 3 || usuario.getUsername().length() > 20) {
            throw new IllegalArgumentException("El nombre de usuario debe tener entre 3 y 20 caracteres");
        }

        if (existeUsername(usuario.getUsername())) {
            throw new IllegalArgumentException("Nombre de usuario ya existe");
        }

        // Validar email
        validarEmail(usuario.getEmail());
        if (existeEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        // Validar password
        validarPassword(usuario.getPassword());

        // Validar edad
        validarEdad(usuario.getFechaNacimiento());
    }

    private void validarPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña es requerida");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
        }

        boolean tieneNumero = password.matches(".*\\d.*");
        boolean tieneLetra = password.matches(".*[a-zA-Z].*");
        boolean tieneSimbolo = password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");

        if (!tieneNumero || !tieneLetra || !tieneSimbolo) {
            throw new IllegalArgumentException("La contraseña debe contener letras, números y símbolos");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El email es requerido");
        }

        if (!EMAIL_REGEX.matcher(email).matches()) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
    }

    private void validarEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es requerida");
        }

        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        if (edad.getYears() < EDAD_MINIMA) {
            throw new IllegalArgumentException("Debes tener al menos " + EDAD_MINIMA + " años para registrarte");
        }
    }
}