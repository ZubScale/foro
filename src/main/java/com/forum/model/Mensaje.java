package com.forum.model;

import java.time.LocalDateTime;

public class Mensaje {
    private String id;
    private String remitenteId;
    private String destinatarioId;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean leido;

    // Constructor vacío
    public Mensaje() {
        this.fechaEnvio = LocalDateTime.now();
        this.leido = false;
    }

    // Constructor con parámetros
    public Mensaje(String id, String remitenteId, String destinatarioId, String contenido) {
        this.id = id;
        this.remitenteId = remitenteId;
        this.destinatarioId = destinatarioId;
        this.contenido = contenido;
        this.fechaEnvio = LocalDateTime.now();
        this.leido = false;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(String remitenteId) {
        this.remitenteId = remitenteId;
    }

    public String getDestinatarioId() {
        return destinatarioId;
    }

    public void setDestinatarioId(String destinatarioId) {
        this.destinatarioId = destinatarioId;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }
}