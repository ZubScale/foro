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
import java.util.Optional;

public class LoginController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    private final UsuarioService usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());

    @FXML
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarError("Por favor, complete todos los campos.");
            return;
        }

        try {
            Usuario usuario = usuarioService.login(username, password);
            SessionManager.login(usuario);
            cargarVistaPrincipal();
        } catch (IllegalArgumentException e) {
            mostrarError("Credenciales inválidas. Verifique su usuario y contraseña.");
        } catch (Exception e) {
            mostrarError("Ocurrió un error inesperado al iniciar sesión. Intente nuevamente.");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            mostrarError("Error al cargar la vista: " + e.getMessage());
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        // Mostrar y esperar confirmación del usuario
        Optional<ButtonType> result = alert.showAndWait();
        result.ifPresent(buttonType -> {
            // Puede agregar lógica adicional al manejar el clic del botón
        });
    }
}