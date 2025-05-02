package com.forum.service;

import com.forum.model.Mensaje;
import java.util.List;

public interface MensajeService {
    void enviarMensaje(Mensaje mensaje);
    List<Mensaje> obtenerConversacionesUsuario(String usuarioId);
    List<Mensaje> obtenerBandejaEntrada(String usuarioId); // Asegurar que existe
    List<Mensaje> obtenerConversacion(String usuarioActualId, String otroUsuarioId);
    List<Mensaje> obtenerMensajesConversacion(String conversacionId);
    void marcarMensajeLeido(String mensajeId);
    void eliminarMensaje(String mensajeId);
}