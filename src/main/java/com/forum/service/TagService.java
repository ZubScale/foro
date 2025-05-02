package com.forum.service;

import com.forum.model.Tag;

import java.util.List;

public interface TagService {
    void crearTag(Tag tag);

    void actualizarTag(Tag tag);

    void eliminarTag(String id);

    List<Tag> listarTodosLosTags();

    Tag obtenerTagPorId(String id);

    Tag obtenerTagPorNombre(String nombre);

    List<Tag> obtenerTodos(); // Added method

    void eliminar(String id);
}