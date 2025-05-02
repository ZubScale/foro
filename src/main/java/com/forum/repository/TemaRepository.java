package com.forum.repository;

import com.forum.model.Tema;
import java.util.List;
import java.util.Optional;

public interface TemaRepository {
    void save(Tema tema);
    void update(Tema tema);
    void delete(String id);
    List<Tema> findAll();
    Optional<Tema> findById(String id);
}