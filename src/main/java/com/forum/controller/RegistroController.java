package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.repository.UsuarioRepositoryImpl;
import com.forum.util.ValidationUtil;
import com.forum.util.ValidationUtil.ValidationResult;
import com.forum.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegistroController {
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private TextField txtFullName;

    private final UsuarioService usuarioService;

    public RegistroController() {
        this.usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());
    }

    @FXML
    public void initialize() {
        configurarValidacionesEnTiempoReal();
        // Se eliminó la configuración del DatePicker
    }

    private void configurarValidacionesEnTiempoReal() {
        // Validación de username
        txtUsername.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                if (!ValidationUtil.isValidUsername(newVal)) {
                    mostrarErrorEnCampo(txtUsername, "Usuario debe tener entre 3 y 20 caracteres");
                } else {
                    limpiarErrorEnCampo(txtUsername);
                }
            }
        });

        // Validación de email
        txtEmail.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                if (!ValidationUtil.isValidEmail(newVal)) {
                    mostrarErrorEnCampo(txtEmail, "Email no válido");
                } else {
                    limpiarErrorEnCampo(txtEmail);
                }
            }
        });

        // Validación de contraseña
        pfPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                ValidationResult result = ValidationUtil.validatePassword(newVal);
                if (!result.isValid()) {
                    mostrarErrorEnCampo(pfPassword, result.getMessage());
                } else {
                    limpiarErrorEnCampo(pfPassword);
                }
            }
        });

        // Validación de nombre completo
        txtFullName.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isEmpty()) {
                if (!ValidationUtil.isValidNombre(newVal)) {
                    mostrarErrorEnCampo(txtFullName, "Nombre no válido");
                } else {
                    limpiarErrorEnCampo(txtFullName);
                }
            }
        });
    }

    @FXML
    private void handleRegistro() {
        if (!validarFormularioCompleto()) {
            mostrarError("Por favor, corrija los errores en el formulario");
            return;
        }

        try {
            Usuario nuevoUsuario = crearUsuario();
            usuarioService.registrar(nuevoUsuario);
            mostrarExito("Registro exitoso");
            redirectToLogin();
        } catch (Exception e) {
            mostrarError("Error al registrar: " + e.getMessage());
        }
    }

    private Usuario crearUsuario() {
        Usuario usuario = new Usuario();
        usuario.setUsername(txtUsername.getText().trim());
        usuario.setEmail(txtEmail.getText().trim());
        usuario.setPassword(pfPassword.getText());

        // Separar nombre completo en nombre y apellido
        String[] partesNombre = txtFullName.getText().trim().split(" ", 2);
        usuario.setNombre(partesNombre[0]);
        if (partesNombre.length > 1) {
            usuario.setApellido(partesNombre[1]);
        }

        // Se eliminó la asignación de fecha de nacimiento

        return usuario;
    }

    private boolean validarFormularioCompleto() {
        boolean isValid = true;

        if (!ValidationUtil.isValidUsername(txtUsername.getText())) {
            isValid = false;
        }
        if (!ValidationUtil.isValidEmail(txtEmail.getText())) {
            isValid = false;
        }
        if (!ValidationUtil.isValidPassword(pfPassword.getText())) {
            isValid = false;
        }
        if (!ValidationUtil.isValidNombre(txtFullName.getText())) {
            isValid = false;
        }

        // Se eliminó la validación de edad

        return isValid;
    }

    @FXML
    private void redirectToLogin() {
        try {
            SceneNavigator.navigateFrom(txtUsername, SceneNavigator.LOGIN_VIEW);
        } catch (Exception e) {
            mostrarError("Error al redirigir al login: " + e.getMessage());
        }
    }

    private void mostrarErrorEnCampo(TextField campo, String mensaje) {
        campo.setStyle("-fx-border-color: #ff6b6b; -fx-border-width: 2px; -fx-border-radius: 5px;");
        campo.setTooltip(new Tooltip(mensaje));
    }

    private void limpiarErrorEnCampo(TextField campo) {
        campo.setStyle("-fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 5px;");
        campo.setTooltip(null);
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarExito(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
