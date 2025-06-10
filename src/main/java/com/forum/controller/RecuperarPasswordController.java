package com.forum.controller;

import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.util.ValidationUtil;
import com.forum.util.EmailSender;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class RecuperarPasswordController {
    @FXML
    private VBox pasoUnoContainer;
    
    @FXML
    private VBox pasoDosContainer;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtCodigo;
    
    @FXML
    private PasswordField txtNewPassword;
    
    @FXML
    private PasswordField txtConfirmPassword;
    
    @FXML
    private Button btnSolicitar;
    
    @FXML
    private Button btnVerificar;
    
    @FXML
    private Button btnCambiar;
    
    @FXML
    private Hyperlink linkVolver;
    
    @FXML
    private Label lblTimer;
    
    @FXML
    private HBox timerContainer;

    private final UsuarioService usuarioService;
    private String codigoVerificacion;
    private static final int TIEMPO_ESPERA = 300; // 5 minutos en segundos
    private java.util.Timer timer;
    private int tiempoRestante;

    public RecuperarPasswordController() {
        this.usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());
    }

    @FXML
    public void initialize() {
        configurarVisibilidad();
        configurarValidaciones();
        configurarEventos();
    }

    private void configurarVisibilidad() {
        pasoUnoContainer.setVisible(true);
        pasoDosContainer.setVisible(false);
        timerContainer.setVisible(false);
    }

    private void configurarValidaciones() {
        // Validación de email
        txtEmail.textProperty().addListener((obs, old, nuevo) -> {
            btnSolicitar.setDisable(!ValidationUtil.isValidEmail(nuevo));
        });

        // Validación de código
        txtCodigo.textProperty().addListener((obs, old, nuevo) -> {
            btnVerificar.setDisable(nuevo.length() != 6);
        });

        // Validaciones de contraseña
        txtNewPassword.textProperty().addListener((obs, old, nuevo) -> {
            validarPasswords();
        });

        txtConfirmPassword.textProperty().addListener((obs, old, nuevo) -> {
            validarPasswords();
        });
    }

    private void configurarEventos() {
        linkVolver.setOnAction(event -> volverALogin());
    }

    @FXML
    private void handleSolicitarCodigo() {
        String email = txtEmail.getText().trim();
        
        if (!ValidationUtil.isValidEmail(email)) {
            mostrarError("Por favor, ingrese un email válido");
            return;
        }

        try {
            if (!usuarioService.existeEmail(email)) {
                mostrarError("No existe una cuenta asociada a este email");
                return;
            }

            codigoVerificacion = generarCodigo();
            EmailSender.enviarCodigoVerificacion(email, codigoVerificacion);
            
            mostrarPasoDos();
            iniciarTimer();
            
        } catch (Exception e) {
            mostrarError("Error al enviar el código: " + e.getMessage());
        }
    }

    @FXML
    private void handleVerificarCodigo() {
        String codigo = txtCodigo.getText().trim();
        
        if (!codigo.equals(codigoVerificacion)) {
            mostrarError("Código inválido");
            return;
        }

        if (tiempoRestante <= 0) {
            mostrarError("El código ha expirado. Por favor, solicite uno nuevo");
            return;
        }

        // Mostrar campos para nueva contraseña
        habilitarCambioPassword();
    }

    @FXML
    private void handleCambiarPassword() {
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();

        if (!validarNuevaPassword(newPassword, confirmPassword)) {
            return;
        }

        try {
            usuarioService.actualizarPassword(txtEmail.getText().trim(), newPassword);
            mostrarExito("Contraseña actualizada exitosamente");
            volverALogin();
        } catch (Exception e) {
            mostrarError("Error al actualizar la contraseña: " + e.getMessage());
        }
    }

    private boolean validarNuevaPassword(String password, String confirmacion) {
        if (!ValidationUtil.isValidPassword(password)) {
            mostrarError("La contraseña no cumple con los requisitos mínimos");
            return false;
        }

        if (!password.equals(confirmacion)) {
            mostrarError("Las contraseñas no coinciden");
            return false;
        }

        return true;
    }

    private void validarPasswords() {
        boolean passwordsValidas = !txtNewPassword.getText().isEmpty() && 
                                 !txtConfirmPassword.getText().isEmpty() &&
                                 txtNewPassword.getText().equals(txtConfirmPassword.getText()) &&
                                 ValidationUtil.isValidPassword(txtNewPassword.getText());
        
        btnCambiar.setDisable(!passwordsValidas);
    }

    private void mostrarPasoDos() {
        pasoUnoContainer.setVisible(false);
        pasoDosContainer.setVisible(true);
        timerContainer.setVisible(true);
        btnCambiar.setVisible(false);
        txtNewPassword.setVisible(false);
        txtConfirmPassword.setVisible(false);
    }

    private void habilitarCambioPassword() {
        txtCodigo.setDisable(true);
        btnVerificar.setVisible(false);
        txtNewPassword.setVisible(true);
        txtConfirmPassword.setVisible(true);
        btnCambiar.setVisible(true);
        detenerTimer();
    }

    private void iniciarTimer() {
        tiempoRestante = TIEMPO_ESPERA;
        if (timer != null) {
            timer.cancel();
        }
        timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new java.util.TimerTask() {
            @Override
            public void run() {
                javafx.application.Platform.runLater(() -> {
                    if (tiempoRestante > 0) {
                        actualizarTimer();
                        tiempoRestante--;
                    } else {
                        detenerTimer();
                    }
                });
            }
        }, 0, 1000);
    }

    private void actualizarTimer() {
        int minutos = tiempoRestante / 60;
        int segundos = tiempoRestante % 60;
        lblTimer.setText(String.format("Tiempo restante: %02d:%02d", minutos, segundos));
    }

    private void detenerTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timerContainer.setVisible(false);
    }

    private String generarCodigo() {
        return String.format("%06d", new java.util.Random().nextInt(999999));
    }

    private void volverALogin() {
        try {
            detenerTimer();
            MainController.cargarVista("login");
        } catch (Exception e) {
            mostrarError("Error al volver al login: " + e.getMessage());
        }
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