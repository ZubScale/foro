package com.forum.controller;

import com.forum.model.Mensaje;
import com.forum.util.JsonUtil;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.stream.Collectors;

public class MensajesController {
    @FXML
    private ListView<Mensaje> lvConversaciones;
    @FXML
    private ListView<Mensaje> lvMensajes;
    @FXML
    private TextArea taNuevoMensaje;

    private static final String MENSAJES_JSON_PATH = "data/mensajes.json";

    @FXML
    private void initialize() {
        cargarConversaciones();
        configurarListeners();
    }

    private void cargarConversaciones() {
        String usuarioId = SessionManager.getUsuarioActual().getId();
        List<Mensaje> todasConversaciones = JsonUtil.cargarDatos(MENSAJES_JSON_PATH, Mensaje.class);

        // Obtener solo las conversaciones donde participa el usuario (remitente o destinatario)
        List<Mensaje> conversacionesUsuario = todasConversaciones.stream()
                .filter(m -> m.getRemitenteId().equals(usuarioId) || m.getDestinatarioId().equals(usuarioId))
                .collect(Collectors.toList());

        lvConversaciones.getItems().setAll(conversacionesUsuario);
    }

    private void cargarMensajesConversacion(Mensaje conversacion) {
        if (conversacion == null) return;

        String usuarioActualId = SessionManager.getUsuarioActual().getId();
        String otroUsuarioId = conversacion.getRemitenteId().equals(usuarioActualId)
                ? conversacion.getDestinatarioId()
                : conversacion.getRemitenteId();

        List<Mensaje> todosMensajes = JsonUtil.cargarDatos(MENSAJES_JSON_PATH, Mensaje.class);

        List<Mensaje> mensajesConversacion = todosMensajes.stream()
                .filter(m ->
                        (m.getRemitenteId().equals(usuarioActualId) && m.getDestinatarioId().equals(otroUsuarioId)) ||
                                (m.getRemitenteId().equals(otroUsuarioId) && m.getDestinatarioId().equals(usuarioActualId))
                )
                .collect(Collectors.toList());

        lvMensajes.getItems().setAll(mensajesConversacion);
    }

    private void configurarListeners() {
        lvConversaciones.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> cargarMensajesConversacion(newVal)
        );
    }

    @FXML
    private void enviarMensaje() {
        String contenido = taNuevoMensaje.getText().trim();
        if (contenido.isEmpty()) return;

        Mensaje conversacionSeleccionada = lvConversaciones.getSelectionModel().getSelectedItem();
        if (conversacionSeleccionada == null) return;

        String remitenteId = SessionManager.getUsuarioActual().getId();
        String destinatarioId = conversacionSeleccionada.getDestinatarioId().equals(remitenteId)
                ? conversacionSeleccionada.getRemitenteId()
                : conversacionSeleccionada.getDestinatarioId();

        Mensaje nuevo = new Mensaje(null, remitenteId, destinatarioId, contenido);

        // Cargar todos los mensajes, agregar el nuevo y guardar
        List<Mensaje> todosMensajes = JsonUtil.cargarDatos(MENSAJES_JSON_PATH, Mensaje.class);
        todosMensajes.add(nuevo);
        JsonUtil.guardarDatos(MENSAJES_JSON_PATH, todosMensajes);

        taNuevoMensaje.clear();
        cargarMensajesConversacion(conversacionSeleccionada);
    }
}
