package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.repository.UsuarioRepositoryImpl;
import com.forum.util.SessionManager;
import com.forum.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class LoginController {
    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private CheckBox cbRememberMe;

    @FXML
    private Button btnLogin;

    @FXML
    private Hyperlink linkRegistro;

    @FXML
    private Hyperlink linkRecuperarPassword;

    private final UsuarioService usuarioService;

    public LoginController() {
        this.usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());
    }

    @FXML
    public void initialize() {
        configurarAnimaciones();
        configurarEventos();
        configurarValidaciones();
        cargarCredencialesGuardadas();

        btnLogin.setDisable(true);
    }

    private void configurarAnimaciones() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(600), txtUsername.getParent());
        translateTransition.setFromY(-20);
        translateTransition.setToY(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(600), txtUsername.getParent());
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        translateTransition.play();
        fadeTransition.play();
    }

    private void configurarEventos() {
        txtUsername.setOnKeyPressed(this::manejarTeclaPresionada);
        pfPassword.setOnKeyPressed(this::manejarTeclaPresionada);

        txtUsername.textProperty().addListener((obs, oldVal, newVal) -> validarCampos());
        pfPassword.textProperty().addListener((obs, oldVal, newVal) -> validarCampos());

        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle("-fx-background-color: linear-gradient(to right, #5a67d8, #6b46c1); -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-cursor: hand; -fx-scale-x: 1.02; -fx-scale-y: 1.02;"));

        btnLogin.setOnMouseExited(e -> btnLogin.setStyle("-fx-background-color: linear-gradient(to right, #667eea, #764ba2); -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-background-radius: 10; -fx-cursor: hand;"));
    }

    private void configurarValidaciones() {
        txtUsername.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                if (newVal.contains(" ")) {
                    txtUsername.setText(newVal.replace(" ", ""));
                }
                if (newVal.length() > 20) {
                    txtUsername.setText(oldVal);
                }
            }
        });

        pfPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.length() > 50) {
                pfPassword.setText(oldVal);
            }
        });
    }

    private void cargarCredencialesGuardadas() {
        String usuarioGuardado = SessionManager.getCredencialesGuardadas();
        if (usuarioGuardado != null && !usuarioGuardado.isEmpty()) {
            txtUsername.setText(usuarioGuardado);
            cbRememberMe.setSelected(true);
            pfPassword.requestFocus();
        } else {
            txtUsername.requestFocus();
        }
    }

    @FXML
    private void handleLogin() {
        deshabilitarControles(true);

        if (!validarEntradas()) {
            deshabilitarControles(false);
            return;
        }

        try {
            String username = txtUsername.getText().trim();
            String password = pfPassword.getText();

            Usuario usuario = usuarioService.login(username, password);
            guardarCredencialesSiNecesario(username);
            SessionManager.login(usuario);
            mostrarBienvenida(usuario.getNombre() != null ? usuario.getNombre() : usuario.getUsername());
            cargarVistaPrincipal();

        } catch (IllegalArgumentException e) {
            mostrarError("Usuario o contraseña incorrectos");
            animarErrorCampos();
            deshabilitarControles(false);
        } catch (Exception e) {
            mostrarError("Error al iniciar sesión: " + e.getMessage());
            deshabilitarControles(false);
        }
    }

    private boolean validarEntradas() {
        String username = txtUsername.getText().trim();
        String password = pfPassword.getText();

        if (username.isEmpty()) {
            mostrarError("Por favor ingrese su nombre de usuario");
            txtUsername.requestFocus();
            return false;
        }

        if (password.isEmpty()) {
            mostrarError("Por favor ingrese su contraseña");
            pfPassword.requestFocus();
            return false;
        }

        if (username.length() < 3) {
            mostrarError("El nombre de usuario debe tener al menos 3 caracteres");
            txtUsername.requestFocus();
            return false;
        }

        return true;
    }

    private void guardarCredencialesSiNecesario(String username) {
        if (cbRememberMe.isSelected()) {
            SessionManager.guardarCredenciales(username);
        } else {
            SessionManager.limpiarCredenciales();
        }
    }

    @FXML
    private void redirectToRegistro() {
        try {
            SceneNavigator.navigateFrom(txtUsername, SceneNavigator.REGISTRO_VIEW);
        } catch (Exception e) {
            mostrarError("Error al cargar la página de registro");
            e.printStackTrace();
        }
    }

    @FXML
    private void redirectToRecuperarPassword() {
        try {
            SceneNavigator.navigateFrom(txtUsername, SceneNavigator.RECUPERAR_PASSWORD_VIEW);
        } catch (Exception e) {
            mostrarError("Error al cargar la página de recuperación");
            e.printStackTrace();
        }
    }

    private void cargarVistaPrincipal() {
        try {
            SceneNavigator.navigateFrom(txtUsername, SceneNavigator.MAIN_VIEW);
        } catch (Exception e) {
            mostrarError("Error al cargar la vista principal");
            e.printStackTrace();
        }
    }

    private void manejarTeclaPresionada(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !btnLogin.isDisabled()) {
            handleLogin();
        } else if (event.getCode() == KeyCode.TAB && event.getSource() == txtUsername) {
            pfPassword.requestFocus();
            event.consume();
        }
    }

    private void validarCampos() {
        boolean camposValidos = !txtUsername.getText().trim().isEmpty() &&
                !pfPassword.getText().isEmpty();
        btnLogin.setDisable(!camposValidos);
    }

    private void deshabilitarControles(boolean deshabilitar) {
        txtUsername.setDisable(deshabilitar);
        pfPassword.setDisable(deshabilitar);
        btnLogin.setDisable(deshabilitar);
        cbRememberMe.setDisable(deshabilitar);
        linkRegistro.setDisable(deshabilitar);
        linkRecuperarPassword.setDisable(deshabilitar);
    }

    private void animarErrorCampos() {
        TranslateTransition ttUsername = new TranslateTransition(Duration.millis(100), txtUsername);
        ttUsername.setFromX(0);
        ttUsername.setToX(-10);
        ttUsername.setCycleCount(4);
        ttUsername.setAutoReverse(true);

        TranslateTransition ttPassword = new TranslateTransition(Duration.millis(100), pfPassword);
        ttPassword.setFromX(0);
        ttPassword.setToX(-10);
        ttPassword.setCycleCount(4);
        ttPassword.setAutoReverse(true);

        ttUsername.play();
        ttPassword.play();

        txtUsername.setStyle(txtUsername.getStyle() + "; -fx-border-color: #ff6b6b;");
        pfPassword.setStyle(pfPassword.getStyle() + "; -fx-border-color: #ff6b6b;");

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                javafx.application.Platform.runLater(() -> {
                    txtUsername.setStyle(txtUsername.getStyle().replace("; -fx-border-color: #ff6b6b;", ""));
                    pfPassword.setStyle(pfPassword.getStyle().replace("; -fx-border-color: #ff6b6b;", ""));
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error de Login");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: white;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px;");

        alert.showAndWait();
    }

    private void mostrarBienvenida(String nombreUsuario) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido");
        alert.setHeaderText(null);
        alert.setContentText("\u00a1Bienvenido " + nombreUsuario + "!");

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: white;");
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 14px;");

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
                javafx.application.Platform.runLater(alert::close);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.setDaemon(true);
        thread.start();

        alert.show();
    }
}
