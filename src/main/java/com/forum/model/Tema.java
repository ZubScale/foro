package com.forum.model;

import java.time.LocalDateTime;

public class Tema {
    private String id;
    private String nombre;
    private String descripcion;
    private String categoriaId;
    private String creadorId;
    private LocalDateTime fechaCreacion;

    public Tema(String nombre, String creadorId, String categoriaId) {
        this.nombre = nombre;
        this.creadorId = creadorId;
        this.categoriaId = categoriaId;
        this.fechaCreacion = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoriaId() { return categoriaId; }
    public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }

    public String getCreadorId() { return creadorId; }
    public void setCreadorId(String creadorId) { this.creadorId = creadorId; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}