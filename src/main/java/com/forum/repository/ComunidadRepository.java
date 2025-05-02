package com.forum.repository;

import com.forum.model.Comunidad;
import java.util.List;
import java.util.Optional;

public interface ComunidadRepository {
    void save(Comunidad comunidad);
    void update(Comunidad comunidad); // ✅ Necesario para actualizar
    void delete(String id); // ✅ Necesario para eliminar
    List<Comunidad> findAll();
    Optional<Comunidad> findById(String id);
    boolean existsById(String id); // ✅ Para validar existencia
}