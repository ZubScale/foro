// CalificacionUsuarioRepository.java
package com.forum.repository;

import com.forum.model.CalificacionUsuario;
import java.util.List;
import java.util.Optional;

public interface CalificacionUsuarioRepository {
    void save(CalificacionUsuario calificacion);
    void update(CalificacionUsuario calificacion); // ✅ Nuevo método
    void delete(String id); // ✅ Nuevo método
    List<CalificacionUsuario> findAll();
    Optional<CalificacionUsuario> findById(String id);
    List<CalificacionUsuario> findByPostId(String postId); // ✅ Nombre corregido
}