package com.forum.controller;

import com.forum.service.RecuperacionPasswordService;
import com.forum.service.RecuperacionPasswordServiceImpl;
import com.forum.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RecuperarPasswordController {
    @FXML
    private TextField txtRecoveryEmail;
    @FXML
    private TextField txtVerificationCode;
    @FXML
    private PasswordField pfNewPassword;
    @FXML
    private Button btnRestablecer;
    @FXML
    private Button VolverAlLogin;

    private final RecuperacionPasswordService passwordService = new RecuperacionPasswordServiceImpl();

    @FXML
    public void initialize() {
        txtVerificationCode.setVisible(false);
        pfNewPassword.setVisible(false);
        btnRestablecer.setVisible(false);
    }

    @FXML
    private void handleEnviarCodigo() {
        String email = txtRecoveryEmail.getText().trim();

        if (email.isEmpty()) {
            mostrarAlerta("Por favor ingrese un correo electrónico.", Alert.AlertType.WARNING);
            return;
        }

        try {
            if (passwordService.validarEmail(email)) {
                passwordService.enviarCodigoRecuperacion(email);
                mostrarCamposRestablecer();
                mostrarAlerta("Código enviado a " + email, Alert.AlertType.INFORMATION);
            } else {
                mostrarAlerta("Email no válido o no registrado", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            mostrarAlerta("Error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleRestablecer() {
        String codigo = txtVerificationCode.getText().trim();
        String nuevaPassword = pfNewPassword.getText().trim();

        if (codigo.isEmpty() || nuevaPassword.isEmpty()) {
            mostrarAlerta("Por favor complete todos los campos.", Alert.AlertType.WARNING);
            return;
        }

        try {
            passwordService.restablecerPassword(codigo, nuevaPassword);
            mostrarAlerta("Contraseña actualizada", Alert.AlertType.INFORMATION);
            NavigationUtil.cambiarVista(btnRestablecer, "/view/login.fxml");
        } catch (IllegalArgumentException e) {
            mostrarAlerta(e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            mostrarAlerta("Error crítico: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleVolverALogin() {
        NavigationUtil.cambiarVista(VolverAlLogin, "/view/login.fxml");
    }

    private void mostrarCamposRestablecer() {
        txtVerificationCode.setVisible(true);
        pfNewPassword.setVisible(true);
        btnRestablecer.setVisible(true);
    }

    private void mostrarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}