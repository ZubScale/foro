package com.forum.service;

import com.forum.model.Mensaje;
import com.forum.repository.MensajeRepository;
import com.forum.repository.MensajeRepositoryImpl;
import java.util.stream.Collectors;
import java.util.List;

public class MensajeServiceImpl implements MensajeService {
    private final MensajeRepository repository = new MensajeRepositoryImpl();

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        validarMensaje(mensaje);
        repository.save(mensaje);
    }

    @Override
    public List<Mensaje> obtenerConversacionesUsuario(String usuarioId) {
        return List.of();
    }

    @Override
    public List<Mensaje> obtenerBandejaEntrada(String usuarioId) {
        return repository.findByUsuario(usuarioId).stream()
                .filter(m -> m.getDestinatarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }


    @Override
    public List<Mensaje> obtenerConversacion(String usuarioActualId, String otroUsuarioId) {
        return repository.findConversacion(usuarioActualId, otroUsuarioId);
    }

    @Override
    public List<Mensaje> obtenerMensajesConversacion(String conversacionId) {
        return List.of();
    }

    @Override
    public void marcarMensajeLeido(String mensajeId) {
        repository.marcarComoLeido(mensajeId);
    }

    @Override
    public void eliminarMensaje(String mensajeId) {
        repository.delete(mensajeId);
    }

    private void validarMensaje(Mensaje mensaje) {
        if (mensaje.getContenido() == null || mensaje.getContenido().isBlank()) {
            throw new IllegalArgumentException("El contenido del mensaje no puede estar vacío");
        }
        if (mensaje.getRemitenteId() == null || mensaje.getDestinatarioId() == null) {
            throw new IllegalArgumentException("Remitente o destinatario inválido");
        }
    }
}