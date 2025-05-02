package com.forum.repository;

import com.forum.model.Mensaje;
import java.util.List;

public interface MensajeRepository {
    void save(Mensaje mensaje);
    List<Mensaje> findByUsuario(String usuarioId);
    List<Mensaje> findConversacion(String usuario1Id, String usuario2Id);
    void marcarComoLeido(String mensajeId);
    void delete(String mensajeId);
}