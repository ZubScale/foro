package com.forum.model;

public class Categoria {
    private String id;
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}