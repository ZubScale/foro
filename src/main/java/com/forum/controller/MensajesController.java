package com.forum.controller;

import com.forum.model.Mensaje;
import com.forum.model.Usuario;
import com.forum.service.MensajeService;
import com.forum.service.MensajeServiceImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

public class MensajesController {
    @FXML private ListView<Mensaje> lvConversaciones;
    @FXML private ListView<Mensaje> lvMensajes;
    @FXML private TextArea taNuevoMensaje;

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

    // Método faltante agregado
    private void cargarMensajesConversacion(Mensaje conversacion) {
        if (conversacion != null) {
            lvMensajes.getItems().setAll(
                    mensajeService.obtenerMensajesConversacion(conversacion.getId())
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
        if (!contenido.isEmpty()) {
            Mensaje nuevo = new Mensaje(
                    SessionManager.getUsuarioActual().getId(),
                    lvConversaciones.getSelectionModel().getSelectedItem().getId(),
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