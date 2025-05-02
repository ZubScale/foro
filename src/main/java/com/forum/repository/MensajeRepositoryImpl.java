package com.forum.repository;

import com.forum.model.Mensaje;
import com.forum.util.JsonUtil;
import java.util.List;
import java.util.stream.Collectors;

public class MensajeRepositoryImpl implements MensajeRepository {
    private static final String FILE_PATH = "data/mensajes.json";

    @Override
    public void save(Mensaje mensaje) {
        List<Mensaje> mensajes = JsonUtil.cargarDatos(FILE_PATH, Mensaje.class);
        mensajes.add(mensaje);
        JsonUtil.guardarDatos(FILE_PATH, mensajes);
    }

    @Override
    public List<Mensaje> findByUsuario(String usuarioId) {
        return JsonUtil.cargarDatos(FILE_PATH, Mensaje.class).stream()
                .filter(m -> m.getRemitenteId().equals(usuarioId) ||
                        m.getDestinatarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Mensaje> findConversacion(String usuario1Id, String usuario2Id) {
        return JsonUtil.cargarDatos(FILE_PATH, Mensaje.class).stream()
                .filter(m -> (m.getRemitenteId().equals(usuario1Id) && m.getDestinatarioId().equals(usuario2Id)) ||
                        (m.getRemitenteId().equals(usuario2Id) && m.getDestinatarioId().equals(usuario1Id)))
                .collect(Collectors.toList());
    }

    @Override
    public void marcarComoLeido(String mensajeId) {
        List<Mensaje> mensajes = JsonUtil.cargarDatos(FILE_PATH, Mensaje.class);
        mensajes.stream()
                .filter(m -> m.getId().equals(mensajeId))
                .findFirst()
                .ifPresent(m -> m.setLeido(true));
        JsonUtil.guardarDatos(FILE_PATH, mensajes);
    }

    @Override
    public void delete(String mensajeId) {
        List<Mensaje> mensajes = JsonUtil.cargarDatos(FILE_PATH, Mensaje.class);
        List<Mensaje> actualizados = mensajes.stream()
                .filter(m -> !m.getId().equals(mensajeId))
                .collect(Collectors.toList());
        JsonUtil.guardarDatos(FILE_PATH, actualizados);
    }
}