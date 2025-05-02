// NotificacionRepository.java
package com.forum.repository;

import com.forum.model.Notificacion;
import java.util.List;
import java.util.Optional;

public interface NotificacionRepository {
    // Asegúrate de incluir TODOS estos métodos
    List<Notificacion> findByUsuario(String usuarioId);
    Optional<Notificacion> findById(String id);
    void save(Notificacion notificacion);
    void delete(String id);
    List<Notificacion> findAll(); // <- ¡Nuevo método requerido!
    boolean existsById(String id); // <- ¡Nuevo método requerido!
}