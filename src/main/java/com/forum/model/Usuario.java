package com.forum.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Usuario {
    private String id;
    private String nombre;
    private String password;
    private String email;
    private LocalDate fechaNacimiento;
    private String descripcion;
    private List<String> comunidades = new ArrayList<>();
    private List<Usuario> seguidores = new ArrayList<>();

    // Constructor principal
    public Usuario(String nombre, String password, String email, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        setValidatedPassword(password);
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<String> getComunidades() { return comunidades; }
    public void setComunidades(List<String> comunidades) { this.comunidades = comunidades; }

    public List<Usuario> getSeguidores() { return seguidores; }
    public void setSeguidores(List<Usuario> seguidores) { this.seguidores = seguidores; }

    // Validación de registro
    public void validarRegistro() {
        if (!validarPassword(this.password)) {
            throw new IllegalArgumentException("La contraseña debe tener 8-12 caracteres, 1 número y 1 símbolo");
        }
        if (!validarEmail(this.email)) {
            throw new IllegalArgumentException("Formato de email inválido");
        }
    }

    private boolean validarPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$");
    }

    private boolean validarEmail(String email) {
        return Pattern.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }

    public boolean isBlank() {
        return (this.nombre == null || this.nombre.trim().isEmpty()) &&
                (this.password == null || this.password.trim().isEmpty()) &&
                (this.email == null || this.email.trim().isEmpty()) &&
                (this.fechaNacimiento == null) &&
                (this.descripcion == null || this.descripcion.trim().isEmpty()) &&
                (this.comunidades == null || this.comunidades.isEmpty()) &&
                (this.seguidores == null || this.seguidores.isEmpty());
    }
    public void setValidatedPassword(String password) {
        if (!validarPassword(password)) {
            throw new IllegalArgumentException("La contraseña debe tener 8-12 caracteres, 1 número y 1 símbolo");
        }
        this.password = password;
    }
}