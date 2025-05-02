package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.repository.UsuarioRepositoryImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;

    private final UsuarioService usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());

    @FXML
    private void handleLogin() {
        try {
            Usuario usuario = usuarioService.login(txtUsername.getText(), txtPassword.getText());
            SessionManager.login(usuario);
            cargarVistaPrincipal();
        } catch (IllegalArgumentException e) {
            mostrarError("Credenciales inválidas");
        }
    }

    // Métodos de navegación añadidos
    @FXML
    private void redirectToRegistro() {
        cargarVista("/view/registro.fxml");
    }

    @FXML
    private void redirectToRecuperarPassword() {
        cargarVista("/view/recuperar_password.fxml");
    }

    private void cargarVistaPrincipal() {
        cargarVista("/view/main.fxml");
    }

    private void cargarVista(String fxmlPath) {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            mostrarError("Error al cargar la vista: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).show();
    }
}