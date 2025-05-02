package com.forum.service;

import com.forum.model.Notificacion;
import com.forum.repository.NotificacionRepository;
import com.forum.repository.NotificacionRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class NotificacionServiceImpl implements NotificacionService {
    private final NotificacionRepository repository = new NotificacionRepositoryImpl();

    @Override
    public List<Notificacion> obtenerNotificacionesUsuario(String usuarioId) {
        return repository.findByUsuario(usuarioId);
    }

    @Override
    public void marcarComoLeida(String notificacionId) {
        Optional<Notificacion> notificacion = repository.findById(notificacionId);
        if (notificacion.isPresent()) {
            notificacion.get().setLeida(true);
            repository.save(notificacion.orElse(null));
        }
    }

    @Override
    public void crearNotificacion(Notificacion notificacion) {
        repository.save(notificacion);
    }
}