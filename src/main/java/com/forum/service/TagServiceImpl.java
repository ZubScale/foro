package com.forum.service;

import com.forum.model.Tag;
import com.forum.repository.TagRepository;
import java.util.List;
import java.util.UUID;

public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void crearTag(Tag tag) {
        if (tag.getNombre() == null || tag.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tag no puede estar vacío");
        }

        // Verificar si ya existe un tag con ese nombre
        try {
            tagRepository.findByNombre(tag.getNombre());
            throw new IllegalArgumentException("Ya existe un tag con ese nombre");
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Tag no encontrado")) {
                // El tag no existe, podemos crearlo
                tag.setId(UUID.randomUUID().toString());
                tagRepository.save(tag);
            } else {
                throw e;
            }
        }
    }

    @Override
    public void actualizarTag(Tag tag) {
        if (tag.getId() == null || tag.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del tag es requerido");
        }

        if (tag.getNombre() == null || tag.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tag no puede estar vacío");
        }

        tagRepository.update(tag);
    }

    @Override
    public void eliminarTag(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("El ID del tag es requerido");
        }
        tagRepository.delete(id);
    }

    @Override
    public List<Tag> listarTodosLosTags() {
        return tagRepository.findAll();
    }

    @Override
    public Tag obtenerTagPorId(String id) {
        return tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag no encontrado"));
    }

    @Override
    public Tag obtenerTagPorNombre(String nombre) {
        return tagRepository.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Tag no encontrado"));
    }

    @Override
    public List<Tag> obtenerTodos() {
        return listarTodosLosTags();
    }

    @Override
    public void eliminar(String id) {
        eliminarTag(id);
    }
}