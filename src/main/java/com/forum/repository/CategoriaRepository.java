package com.forum.repository;

import com.forum.model.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {
    void save(Categoria categoria);
    void update(Categoria categoria); // Debe existir
    void delete(String id); // Debe existir
    List<Categoria> findAll();
    Optional<Categoria> findById(String id);
    Optional<Categoria> findByNombre(String nombre);
    boolean existsById(String id); // ✅ Añadir este método

}