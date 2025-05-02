package com.forum.model;

import java.time.LocalDateTime;

public class Comentario {
    private String id;
    private String usuarioId;
    private String contenido;
    private LocalDateTime fecha;

    public Comentario(String usuarioId, String contenido) {
        this.usuarioId = usuarioId;
        this.contenido = contenido;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}