package com.forum.model;

public class Tag {
    private String id;
    private String nombre;
    private int contadorUso;

    public Tag(String nombre) {
        this.nombre = nombre;
        this.contadorUso = 0;
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getContadorUso() { return contadorUso; }
    public void setContadorUso(int contadorUso) { this.contadorUso = contadorUso; }

    public void incrementarUso() {
        contadorUso++;
    }
}