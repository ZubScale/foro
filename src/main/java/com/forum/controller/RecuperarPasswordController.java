package com.forum.controller;

import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.repository.UsuarioRepositoryImpl;
import com.forum.util.ValidationUtil;
import com.forum.util.EmailSender;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Timer;
import java.util.TimerTask;

public class RecuperarPasswordController {
    @FXML
    private TextField txtRecoveryEmail;

    @FXML
    private TextField txtVerificationCode;

    @FXML
    private PasswordField pfNewPassword;

    @FXML
    private Button btnRestablecer;

    private final UsuarioService usuarioService;
    private String codigoVerificacion;
    private String emailActual;
    private Timer timer;
    private static final int TIEMPO_EXPIRACION = 300; // 5 minutos en segundos

    public RecuperarPasswordController() {
        this.usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());
    }

    @FXML
    public void initialize() {
        // Configurar visibilidad inicial
        txtVerificationCode.setVisible(false);
        pfNewPassword.setVisible(false);
        btnRestablecer.setVisible(false);
    }

    @FXML
    private void handleEnviarCodigo() {
        String email = txtRecoveryEmail.getText().trim();

        if (!ValidationUtil.isValidEmail(email)) {
            mostrarError("Por favor, ingrese un email válido");
            return;
        }

        try {
            if (!usuarioService.existeEmail(email)) {
                mostrarError("No existe una cuenta asociada a este email");
                return;
            }

            emailActual = email;
            codigoVerificacion = generarCodigo();
            EmailSender.enviarCodigoVerificacion(email, codigoVerificacion);

            mostrarCamposVerificacion();
            iniciarTimer();

            mostrarInfo("Se ha enviado un código de verificación a tu email. Revisa la consola para ver el código.");

        } catch (Exception e) {
            mostrarError("Error al enviar el código: " + e.getMessage());
        }
    }

    @FXML
    private void handleRestablecer() {
        String codigo = txtVerificationCode.getText().trim();
        String nuevaPassword = pfNewPassword.getText();

        if (!codigo.equals(codigoVerificacion)) {
            mostrarError("Código de verificación incorrecto");
            return;
        }

        if (!ValidationUtil.isValidPassword(nuevaPassword)) {
            mostrarError("La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas, números y caracteres especiales");
            return;
        }

        try {
            usuarioService.actualizarPassword(emailActual, nuevaPassword);
            detenerTimer();
            mostrarExito("Contraseña actualizada exitosamente");
            handleVolverALogin();
        } catch (Exception e) {
            mostrarError("Error al actualizar la contraseña: " + e.getMessage());
        }
    }

    @FXML
    private void handleVolverALogin() {
        try {
            detenerTimer();
            Stage stage = (Stage) txtRecoveryEmail.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (Exception e) {
            mostrarError("Error al volver al login: " + e.getMessage());
        }
    }

    private void mostrarCamposVerificacion() {
        txtRecoveryEmail.setDisable(true);
        txtVerificationCode.setVisible(true);
        pfNewPassword.setVisible(true);
        btnRestablecer.setVisible(true);
    }

    private String generarCodigo() {
        return String.format("%06d", new java.util.Random().nextInt(999999));
    }

    private void iniciarTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    codigoVerificacion = null;
                    mostrarError("El código de verificación ha expirado. Por favor, solicite uno nuevo.");
                    resetearFormulario();
                });
            }
        }, TIEMPO_EXPIRACION * 1000);
    }

    private void detenerTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void resetearFormulario() {
        txtRecoveryEmail.setDisable(false);
        txtRecoveryEmail.clear();
        txtVerificationCode.setVisible(false);
        txtVerificationCode.clear();
        pfNewPassword.setVisible(false);
        pfNewPassword.clear();
        btnRestablecer.setVisible(false);
        detenerTimer();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
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