package com.forum.repository;

import com.forum.model.TipoCalificacion;
import java.util.List;
import java.util.Optional;

public interface TipoCalificacionRepository {
    void save(TipoCalificacion tipo);
    void update(TipoCalificacion tipo);
    void delete(String id);
    List<TipoCalificacion> findAll();
    Optional<TipoCalificacion> findById(String id);
    Optional<TipoCalificacion> findByDescripcion(String descripcion);
}