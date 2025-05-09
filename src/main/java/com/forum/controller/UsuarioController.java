package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.repository.UsuarioRepositoryImpl;
import com.forum.util.SessionManager;
import com.forum.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UsuarioController {

    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextArea txtBio;

    private final UsuarioService usuarioService =
            new UsuarioServiceImpl(new UsuarioRepositoryImpl());

    @FXML
    public void initialize() {
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Usuario usuario = SessionManager.getUsuarioActual();
        if (usuario == null) {
            mostrarAlerta("No se pudo cargar los datos del usuario actual", Alert.AlertType.ERROR);
            return;
        }
        txtUsername.setText(usuario.getNombre() != null ? usuario.getNombre() : "");
        txtEmail.setText(usuario.getEmail() != null ? usuario.getEmail() : "");
        txtBio.setText(usuario.getDescripcion() != null ? usuario.getDescripcion() : "");
    }

    @FXML
    private void guardarPerfil() {
        try {
            validarCampos();

            Usuario usuarioActualizado = SessionManager.getUsuarioActual();
            if (usuarioActualizado == null) {
                mostrarAlerta("No se encontró un usuario en la sesión actual", Alert.AlertType.ERROR);
                return;
            }

            usuarioActualizado.setNombre(txtUsername.getText().trim());
            usuarioActualizado.setEmail(txtEmail.getText().trim());
            usuarioActualizado.setDescripcion(txtBio.getText().trim());

            usuarioService.actualizarUsuario(usuarioActualizado);
            mostrarAlerta("Perfil actualizado correctamente", Alert.AlertType.INFORMATION);

        } catch (IllegalArgumentException e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Ocurrió un error inesperado", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cambiarPassword() {
        try {
            NavigationUtil.cambiarVista(
                    (Stage) txtUsername.getScene().getWindow(),
                    "/view/recuperar_password.fxml"
            );
        } catch (Exception e) {
            mostrarAlerta("No se pudo navegar a la vista de cambiar contraseña", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void volverAlMain() {
        try {
            NavigationUtil.cambiarVista(
                    (Stage) txtUsername.getScene().getWindow(),
                    "/view/main.fxml"
            );
        } catch (Exception e) {
            mostrarAlerta("No se pudo navegar a la vista principal", Alert.AlertType.ERROR);
        }
    }

    private void validarCampos() {
        if (txtUsername.getText() == null || txtUsername.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        String email = txtEmail.getText();
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("Email no válido");
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo, mensaje, ButtonType.OK);
        alerta.showAndWait();
    }
}