package com.forum.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Comunidad {
    private String id;
    private String nombre;
    private String descripcion;
    private String reglas;
    private String categoria;
    private String privacidad;
    private LocalDate fechaCreacion;
    private String creadorId;
    private List<String> miembros = new ArrayList<>();
    private List<String> moderadores = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    // Constructor vacío
    public Comunidad() {
        this.fechaCreacion = LocalDate.now();
    }

    // Constructor con parámetros básicos
    public Comunidad(String nombre, String creadorId) {
        this.nombre = nombre;
        this.creadorId = creadorId;
        this.fechaCreacion = LocalDate.now();
        this.miembros.add(creadorId);
        this.moderadores.add(creadorId);
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getReglas() {
        return reglas;
    }

    public void setReglas(String reglas) {
        this.reglas = reglas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getPrivacidad() {
        return privacidad;
    }

    public void setPrivacidad(String privacidad) {
        this.privacidad = privacidad;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getCreadorId() {
        return creadorId;
    }

    public void setCreadorId(String creadorId) {
        this.creadorId = creadorId;
    }

    public List<String> getMiembros() {
        return miembros;
    }

    public void setMiembros(List<String> miembros) {
        this.miembros = miembros;
    }

    public List<String> getModeradores() {
        return moderadores;
    }

    public void setModeradores(List<String> moderadores) {
        this.moderadores = moderadores;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    // Métodos de membresía
    public void seguirComunidad(String usuarioId) {
        if (!miembros.contains(usuarioId)) {
            miembros.add(usuarioId);
        }
    }

    public void dejarComunidad(String usuarioId) {
        miembros.remove(usuarioId);
    }

    public void agregarModerador(String usuarioId) {
        if (!moderadores.contains(usuarioId)) {
            moderadores.add(usuarioId);
        }
    }

    public void quitarModerador(String usuarioId) {
        moderadores.remove(usuarioId);
    }

    public boolean esModerador(String usuarioId) {
        return moderadores.contains(usuarioId);
    }

    public boolean esMiembro(String usuarioId) {
        return miembros.contains(usuarioId);
    }

    public int getCantidadMiembros() {
        return miembros.size();
    }
}