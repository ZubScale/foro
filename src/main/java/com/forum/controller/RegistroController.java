package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.repository.UsuarioRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistroController {
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dateFechaNacimiento;
    @FXML
    private Label lblError;
    @FXML
    private Button redirectToLogin;

    private final UsuarioService usuarioService =
            new UsuarioServiceImpl(new UsuarioRepositoryImpl());

    @FXML
    private void handleRegistro() {
        lblError.setText(""); // Clear previous error messages
        try {
            if (txtUsername.getText().isBlank() || txtPassword.getText().isBlank() ||
                    txtEmail.getText().isBlank() || dateFechaNacimiento.getValue() == null) {
                lblError.setText("Todos los campos son obligatorios");
                return;
            }

            Usuario nuevoUsuario = new Usuario(
                    txtUsername.getText().trim(),
                    txtPassword.getText(),
                    txtEmail.getText().trim(),
                    dateFechaNacimiento.getValue()
            );

            usuarioService.registrarUsuario(nuevoUsuario);
            MainController.cargarVista("login");
        } catch (IllegalArgumentException e) {
            lblError.setText(e.getMessage());
        }
    }

    @FXML
    private void redirectToLogin() {
        MainController.cargarVista("login");
    }
}