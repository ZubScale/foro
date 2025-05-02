package com.forum.service;

import com.forum.model.Tag;
import com.forum.repository.TagRepository;
import com.forum.repository.TagRepositoryImpl;

import java.util.List;

public class TagServiceImpl implements TagService {
    private TagRepository tagsRepo;

    public TagServiceImpl(TagRepositoryImpl tagRepository) {

    }

    public void TagsServiceImpl(TagRepository tagsRepo) {
        this.tagsRepo = tagsRepo;
    }

    @Override
    public void crearTag(Tag tag) {
        if (tag.getNombre() == null || tag.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tag no puede estar vacío");
        }
        tagsRepo.save(tag);
    }

    @Override
    public void actualizarTag(Tag tag) {
        tagsRepo.update(tag);
    }

    @Override
    public void eliminarTag(String id) {
        tagsRepo.delete(id);
    }

    @Override
    public List<Tag> listarTodosLosTags() {
        return tagsRepo.findAll();
    }

    @Override
    public Tag obtenerTagPorId(String id) {
        return tagsRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag no encontrado"));
    }

    @Override
    public Tag obtenerTagPorNombre(String nombre) {
        return tagsRepo.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Tag no encontrado"));
    }

    @Override
    public List<Tag> obtenerTodos() {
        return List.of();
    }

    @Override
    public void eliminar(String id) {

    }
}