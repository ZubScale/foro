package com.forum.controller;

import com.forum.model.Mensaje;
import com.forum.service.MensajeService;
import com.forum.service.MensajeServiceImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class MensajesController {
    @FXML
    private ListView<Mensaje> lvConversaciones;
    @FXML
    private ListView<Mensaje> lvMensajes;
    @FXML
    private TextArea taNuevoMensaje;

    private final MensajeService mensajeService = new MensajeServiceImpl();

    @FXML
    private void initialize() {
        cargarConversaciones();
        configurarListeners();
    }

    private void cargarConversaciones() {
        // Obtener conversaciones del usuario actual
        String usuarioId = SessionManager.getUsuarioActual().getId();
        lvConversaciones.getItems().setAll(
                mensajeService.obtenerConversacionesUsuario(usuarioId)
        );
    }

    private void cargarMensajesConversacion(Mensaje conversacion) {
        if (conversacion != null) {
            lvMensajes.getItems().setAll(
                    mensajeService.obtenerConversacion(
                            SessionManager.getUsuarioActual().getId(),
                            conversacion.getRemitenteId().equals(SessionManager.getUsuarioActual().getId())
                                    ? conversacion.getDestinatarioId()
                                    : conversacion.getRemitenteId()
                    )
            );
        }
    }

    private void configurarListeners() {
        lvConversaciones.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldVal, newVal) -> cargarMensajesConversacion(newVal)
        );
    }

    @FXML
    private void enviarMensaje() {
        String contenido = taNuevoMensaje.getText();
        if (!contenido.isEmpty() && lvConversaciones.getSelectionModel().getSelectedItem() != null) {
            Mensaje nuevo = new Mensaje(
                    null,
                    SessionManager.getUsuarioActual().getId(),
                    lvConversaciones.getSelectionModel().getSelectedItem().getDestinatarioId().equals(SessionManager.getUsuarioActual().getId())
                            ? lvConversaciones.getSelectionModel().getSelectedItem().getRemitenteId()
                            : lvConversaciones.getSelectionModel().getSelectedItem().getDestinatarioId(),
                    contenido
            );
            mensajeService.enviarMensaje(nuevo);
            taNuevoMensaje.clear();
            cargarMensajesConversacion(
                    lvConversaciones.getSelectionModel().getSelectedItem()
            );
        }
    }
}