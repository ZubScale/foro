package com.forum.model;

public class TipoCalificacion {
    private String id;
    private String descripcion;

    public TipoCalificacion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}