package com.forum.repository;

import com.forum.model.Notificacion;
import com.forum.util.JsonUtil;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NotificacionRepositoryImpl implements NotificacionRepository {
    private static final String FILE_PATH = "data/notificaciones.json";

    @Override
    public List<Notificacion> findByUsuario(String usuarioId) {
        List<Notificacion> notificaciones = JsonUtil.cargarDatos(FILE_PATH, Notificacion.class);
        return notificaciones.stream()
                .filter(n -> n.getUsuarioDestinoId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Notificacion> findById(String id) {
        List<Notificacion> notificaciones = JsonUtil.cargarDatos(FILE_PATH, Notificacion.class);
        return notificaciones.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst();
    }

    @Override
    public void save(Notificacion notificacion) {
        List<Notificacion> notificaciones = JsonUtil.cargarDatos(FILE_PATH, Notificacion.class);
        notificaciones.add(notificacion);
        JsonUtil.guardarDatos(FILE_PATH, notificaciones);
    }

    @Override
    public void delete(String id) { // Nombre de parámetro consistente
        List<Notificacion> notificaciones = JsonUtil.cargarDatos(FILE_PATH, Notificacion.class);
        List<Notificacion> actualizadas = notificaciones.stream()
                .filter(n -> !n.getId().equals(id))
                .collect(Collectors.toList());
        JsonUtil.guardarDatos(FILE_PATH, actualizadas);
    }

    @Override
    public List<Notificacion> findAll() {
        return JsonUtil.cargarDatos(FILE_PATH, Notificacion.class);
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }
}