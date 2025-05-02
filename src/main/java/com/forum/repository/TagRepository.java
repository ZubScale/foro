package com.forum.repository;

import com.forum.model.Tag;
import java.util.List;
import java.util.Optional;

public interface TagRepository {
    void save(Tag tag);
    void update(Tag tag);
    void delete(String id);
    List<Tag> findAll();
    Optional<Tag> findById(String id);
    Optional<Tag> findByNombre(String nombre);
}