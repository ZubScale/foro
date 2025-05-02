package com.forum.model;

import java.time.LocalDateTime;

public class CalificacionUsuario {
    private String id;
    private String postId;
    private String usuarioId;
    private String tipoCalificacionId;
    private LocalDateTime fecha;

    public CalificacionUsuario(String postId, String usuarioId, String tipoCalificacionId) {
        this.postId = postId;
        this.usuarioId = usuarioId;
        this.tipoCalificacionId = tipoCalificacionId;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getTipoCalificacionId() { return tipoCalificacionId; }
    public void setTipoCalificacionId(String tipoCalificacionId) { this.tipoCalificacionId = tipoCalificacionId; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}