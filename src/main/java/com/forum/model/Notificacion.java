package com.forum.model;

import java.time.LocalDateTime;

public class Notificacion {
    private String id;
    private String usuarioDestinoId;
    private String tipo;
    private String contenido;
    private LocalDateTime fecha;
    private boolean leida;

    public Notificacion(String usuarioDestinoId, String tipo, String contenido) {
        this.usuarioDestinoId = usuarioDestinoId;
        this.tipo = tipo;
        this.contenido = contenido;
        this.fecha = LocalDateTime.now();
        this.leida = false;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioDestinoId() { return usuarioDestinoId; }
    public void setUsuarioDestinoId(String usuarioDestinoId) { this.usuarioDestinoId = usuarioDestinoId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public boolean isLeida() { return leida; }
    public void setLeida(boolean leida) { this.leida = leida; }
}