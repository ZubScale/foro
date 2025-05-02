package com.forum.controller;

import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PerfilUsuarioController {
    @FXML private Label lblNombre;
    @FXML private Label lblEmail;

    @FXML
    private void initialize() {
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        lblNombre.setText(SessionManager.getUsuarioActual().getNombre());
        lblEmail.setText(SessionManager.getUsuarioActual().getEmail());
    }
}