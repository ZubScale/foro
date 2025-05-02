package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.repository.UsuarioRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

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
    private Button RedirectToLogin;

    private final UsuarioService usuarioService =
            new UsuarioServiceImpl(new UsuarioRepositoryImpl());

    @FXML
    private void handleRegistro() {
        try {
            Usuario nuevoUsuario = new Usuario(
                    txtUsername.getText(),
                    txtPassword.getText(),
                    txtEmail.getText(),
                    dateFechaNacimiento.getValue()
            );

            usuarioService.registrarUsuario(nuevoUsuario);
            // Navegar a login
        } catch (IllegalArgumentException e) {
            lblError.setText(e.getMessage());
        }
    }

    @FXML
    private void redirectToLogin() {
        MainController.cargarVista("login");
    }
}