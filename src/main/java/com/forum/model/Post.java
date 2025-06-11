package com.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String id;
    private String titulo;
    private String contenido;
    private String usuarioId;
    private String comunidadId;
    private LocalDateTime fecha;
    private List<String> tags = new ArrayList<>();
    private int votos;
    private List<Comentario> comentarios = new ArrayList<>();

    // Constructor vacío
    public Post() {
        this.fecha = LocalDateTime.now();
        this.votos = 0;
    }

    // Constructor con parámetros básicos
    public Post(String titulo, String contenido, String usuarioId) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuarioId = usuarioId;
        this.fecha = LocalDateTime.now();
        this.votos = 0;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getComunidadId() {
        return comunidadId;
    }

    public void setComunidadId(String comunidadId) {
        this.comunidadId = comunidadId;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    // Métodos de utilidad
    public void agregarTag(String tag) {
        if (!tags.contains(tag)) {
            tags.add(tag);
        }
    }

    public void quitarTag(String tag) {
        tags.remove(tag);
    }

    public void agregarComentario(Comentario comentario) {
        comentarios.add(comentario);
    }

    public void votarPositivo() {
        votos++;
    }

    public void votarNegativo() {
        if (votos > 0) {
            votos--;
        }
    }

    public int getCantidadComentarios() {
        return comentarios.size();
    }
}