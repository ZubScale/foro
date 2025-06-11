package com.forum.controller;

import com.forum.model.Usuario;
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
    public void initialize() {
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Usuario usuario = SessionManager.getUsuarioActual();

        if (usuario == null) {
            mostrarAlerta("Error", "No hay una sesión de usuario activa.");
            return;
        }

        lblNombre.setText(usuario.getNombre() != null ? usuario.getNombre() : "Sin nombre");
        lblEmail.setText(usuario.getEmail() != null ? usuario.getEmail() : "Sin email");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
