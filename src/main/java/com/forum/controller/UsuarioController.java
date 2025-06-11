package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.repository.UsuarioRepositoryImpl;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.util.NavigationUtil;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UsuarioController {

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextArea txtBio;

    private final UsuarioService usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());

    @FXML
    private void initialize() {
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Usuario usuario = SessionManager.getUsuarioActual();
        if (usuario == null) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo cargar los datos del usuario actual.");
            return;
        }

        txtUsername.setText(defaultIfNull(usuario.getNombre()));
        txtEmail.setText(defaultIfNull(usuario.getEmail()));
        txtBio.setText(defaultIfNull(usuario.getDescripcion()));
    }

    @FXML
    private void guardarPerfil() {
        try {
            validarCampos();

            Usuario usuario = SessionManager.getUsuarioActual();
            if (usuario == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se encontró un usuario en la sesión.");
                return;
            }

            usuario.setNombre(txtUsername.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setDescripcion(txtBio.getText().trim());

            usuarioService.actualizarUsuario(usuario);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Perfil actualizado correctamente.");

        } catch (IllegalArgumentException ex) {
            mostrarAlerta(Alert.AlertType.WARNING, "Validación", ex.getMessage());
        } catch (Exception ex) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error inesperado.");
            ex.printStackTrace();
        }
    }

    @FXML
    private void cambiarPassword() {
        navegarAScene("/view/recuperar_password.fxml", "No se pudo abrir la vista para cambiar la contraseña.");
    }

    @FXML
    private void volverAlMain() {
        navegarAScene("/view/main.fxml", "No se pudo volver a la vista principal.");
    }

    private void validarCampos() {
        String nombre = txtUsername.getText();
        String email = txtEmail.getText();

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario es obligatorio.");
        }
        if (email == null || email.trim().isEmpty() || !email.contains("@")) {
            throw new IllegalArgumentException("El correo electrónico no es válido.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void navegarAScene(String rutaVista, String mensajeError) {
        try {
            Stage stage = (Stage) txtUsername.getScene().getWindow();
            NavigationUtil.cambiarVista(stage, rutaVista);
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", mensajeError);
            e.printStackTrace();
        }
    }

    private String defaultIfNull(String valor) {
        return valor != null ? valor : "";
    }
}
