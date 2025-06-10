package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.repository.UsuarioRepositoryImpl;
import com.forum.util.SessionManager;
import com.forum.util.ValidationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class LoginController {
    @FXML
    private TextField txtUsername;
    
    @FXML
    private PasswordField txtPassword;
    
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
        configurarEventos();
        configurarValidaciones();
        cargarCredencialesGuardadas();
    }

    private void configurarEventos() {
        // Configurar evento de Enter en los campos
        txtUsername.setOnKeyPressed(this::manejarEnter);
        txtPassword.setOnKeyPressed(this::manejarEnter);

        // Habilitar/deshabilitar botón según contenido
        txtUsername.textProperty().addListener((obs, old, nuevo) -> validarCampos());
        txtPassword.textProperty().addListener((obs, old, nuevo) -> validarCampos());
    }

    private void configurarValidaciones() {
        // Limitar longitud de campos
        txtUsername.textProperty().addListener((obs, old, nuevo) -> {
            if (nuevo.length() > 50) {
                txtUsername.setText(old);
            }
        });

        // No permitir espacios en el usuario
        txtUsername.textProperty().addListener((obs, old, nuevo) -> {
            if (nuevo.contains(" ")) {
                txtUsername.setText(nuevo.replace(" ", ""));
            }
        });
    }

    private void cargarCredencialesGuardadas() {
        // Si existen credenciales guardadas, cargarlas
        String usuarioGuardado = SessionManager.getCredencialesGuardadas();
        if (usuarioGuardado != null && !usuarioGuardado.isEmpty()) {
            txtUsername.setText(usuarioGuardado);
            cbRememberMe.setSelected(true);
        }
    }

    @FXML
    private void handleLogin() {
        if (!validarEntradas()) {
            return;
        }

        try {
            String username = txtUsername.getText().trim();
            String password = txtPassword.getText().trim();

            Usuario usuario = usuarioService.login(username, password);
            
            if (usuario != null) {
                guardarCredencialesSiNecesario(username);
                SessionManager.login(usuario);
                MainController.cargarVista("main");
            } else {
                mostrarError("Credenciales inválidas");
            }
        } catch (IllegalArgumentException e) {
            mostrarError("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        }
    }

    private boolean validarEntradas() {
        StringBuilder errores = new StringBuilder();

        if (txtUsername.getText().trim().isEmpty()) {
            errores.append("El nombre de usuario es requerido\n");
        }

        if (txtPassword.getText().trim().isEmpty()) {
            errores.append("La contraseña es requerida\n");
        }

        if (errores.length() > 0) {
            mostrarError(errores.toString());
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
            MainController.cargarVista("registro");
        } catch (Exception e) {
            mostrarError("Error al cargar la página de registro: " + e.getMessage());
        }
    }

    @FXML
    private void redirectToRecuperarPassword() {
        try {
            MainController.cargarVista("recuperar_password");
        } catch (Exception e) {
            mostrarError("Error al cargar la página de recuperación: " + e.getMessage());
        }
    }

    private void manejarEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLogin();
        }
    }

    private void validarCampos() {
        boolean camposCompletos = !txtUsername.getText().trim().isEmpty() 
                                && !txtPassword.getText().trim().isEmpty();
        btnLogin.setDisable(!camposCompletos);
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
}