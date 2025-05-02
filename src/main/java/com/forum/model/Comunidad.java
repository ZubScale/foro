package com.forum.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comunidad {
    private String id;
    private String nombre;
    private String descripcion;
    private LocalDate fechaCreacion;
    private String creadorId;
    private List<String> miembros = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    public Comunidad(String text, String id, String nombre, String creadorId) {
        this.nombre = nombre;
        this.creadorId = creadorId;
        this.fechaCreacion = LocalDate.now();
        this.miembros.add(creadorId);
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getCreadorId() { return creadorId; }
    public void setCreadorId(String creadorId) { this.creadorId = creadorId; }

    public List<String> getMiembros() { return miembros; }
    public void setMiembros(List<String> miembros) { this.miembros = miembros; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    // Métodos de membresía
    public void seguirComunidad(String usuarioId) {
        if (!miembros.contains(usuarioId)) {
            miembros.add(usuarioId);
        }
    }

    public void dejarComunidad(String usuarioId) {
        miembros.remove(usuarioId);
    }
}