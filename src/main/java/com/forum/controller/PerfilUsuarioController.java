package com.forum.controller;

import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

public class PerfilUsuarioController {
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblEmail;

    @FXML
    private void initialize() {
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        if (SessionManager.getUsuarioActual() == null) {
            mostrarError("Error", "No se encontró un usuario activo.");
            return;
        }

        lblNombre.setText(SessionManager.getUsuarioActual().getNombre());
        lblEmail.setText(SessionManager.getUsuarioActual().getEmail());
    }

    private void mostrarError(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}