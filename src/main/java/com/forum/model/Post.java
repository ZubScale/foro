package com.forum.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post {
    private String id;
    private String titulo;       // Campo requerido
    private String contenido;
    private String usuarioId;
    private LocalDateTime fecha;
    private List<String> tags = new ArrayList<>();
    private int votos;
    private List<Comentario> comentarios = new ArrayList<>();

    // Constructor
    public Post(String titulo, String contenido, Usuario usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuarioId = usuario.getId();
        this.fecha = LocalDateTime.now();
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
    }          // Getter

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    } // Setter

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

    // Setter para votos
    public void setVotos(int votos) {
        this.votos = votos;
    }

    // Getter para comentarios
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    // Setter para comentarios
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }
}
