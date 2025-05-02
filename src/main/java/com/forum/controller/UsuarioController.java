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

    @FXML private TextField txtUsername;
    @FXML private TextField txtEmail;
    @FXML private TextArea txtBio;

    private final UsuarioService usuarioService =
            new UsuarioServiceImpl(new UsuarioRepositoryImpl());

    @FXML
    public void initialize() {
        cargarDatosUsuario();
    }

    private void cargarDatosUsuario() {
        Usuario usuario = SessionManager.getUsuarioActual();
        txtUsername.setText(usuario.getNombre());
        txtEmail.setText(usuario.getEmail());
        txtBio.setText(usuario.getDescripcion());
    }

    @FXML
    private void guardarPerfil() {
        try {
            validarCampos();

            Usuario usuarioActualizado = SessionManager.getUsuarioActual();
            usuarioActualizado.setNombre(txtUsername.getText());
            usuarioActualizado.setEmail(txtEmail.getText());
            usuarioActualizado.setDescripcion(txtBio.getText());

            usuarioService.actualizarUsuario(usuarioActualizado);
            mostrarAlerta("Perfil actualizado correctamente", Alert.AlertType.INFORMATION);

        } catch (IllegalArgumentException e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void cambiarPassword() {
        NavigationUtil.cambiarVista(
                (Stage) txtUsername.getScene().getWindow(),
                "/view/recuperar_password.fxml"
        );
    }

    @FXML
    private void volverAlMain() {
        NavigationUtil.cambiarVista(
                (Stage) txtUsername.getScene().getWindow(),
                "/view/main.fxml"
        );
    }

    private void validarCampos() {
        if (txtUsername.getText().isBlank()) {
            throw new IllegalArgumentException("El nombre de usuario es requerido");
        }
        if (txtEmail.getText().isBlank() || !txtEmail.getText().contains("@")) {
            throw new IllegalArgumentException("Email no válido");
        }
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        new Alert(tipo, mensaje, ButtonType.OK).show();
    }
}