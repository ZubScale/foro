package com.forum.model;

import java.time.LocalDateTime;

public class Mensaje {
    private String id;
    private String remitenteId;
    private String destinatarioId;
    private String contenido;
    private LocalDateTime fechaEnvio;
    private boolean leido;

    public Mensaje(String id, String s, String contenido) {
        this.remitenteId = remitenteId;
        this.destinatarioId = destinatarioId;
        this.contenido = this.contenido;
        this.fechaEnvio = LocalDateTime.now();
        this.leido = false;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRemitenteId() { return remitenteId; }
    public void setRemitenteId(String remitenteId) { this.remitenteId = remitenteId; }

    public String getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(String destinatarioId) { this.destinatarioId = destinatarioId; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public boolean isLeido() { return leido; }
    public void setLeido(boolean leido) { this.leido = leido; }
}