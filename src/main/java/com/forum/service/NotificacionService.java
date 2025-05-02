package com.forum.service;

import com.forum.model.Notificacion;

import java.util.List;

public interface NotificacionService {
    List<Notificacion> obtenerNotificacionesUsuario(String usuarioId);
    void marcarComoLeida(String notificacionId);
    void crearNotificacion(Notificacion notificacion);
}