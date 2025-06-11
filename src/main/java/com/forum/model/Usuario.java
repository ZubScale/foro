package com.forum.model;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Usuario {
    private String id;
    private String username;
    private String nombre;
    private String apellido;
    private String password;
    private String email;
    private String descripcion;
    private List<String> comunidades = new ArrayList<>();
    private List<String> seguidores = new ArrayList<>();
    private boolean isAdmin = false;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor principal sin fechaNacimiento
    public Usuario(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getComunidades() {
        return comunidades;
    }

    public void setComunidades(List<String> comunidades) {
        this.comunidades = comunidades;
    }

    public List<String> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(List<String> seguidores) {
        this.seguidores = seguidores;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

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
        return password != null && password.matches("^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,12}$");
    }

    private boolean validarEmail(String email) {
        return email != null && Pattern.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$", email);
    }

    public boolean isBlank() {
        return (this.username == null || this.username.trim().isEmpty()) &&
                (this.nombre == null || this.nombre.trim().isEmpty()) &&
                (this.password == null || this.password.trim().isEmpty()) &&
                (this.email == null || this.email.trim().isEmpty());
    }

    public void setValidatedPassword(String password) {
        if (!validarPassword(password)) {
            throw new IllegalArgumentException("La contraseña debe tener 8-12 caracteres, 1 número y 1 símbolo");
        }
        this.password = password;
    }
}
