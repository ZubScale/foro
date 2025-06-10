package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.service.UsuarioService;
import com.forum.service.UsuarioServiceImpl;
import com.forum.util.ValidationUtil;
import com.forum.util.ValidationUtil.ValidationResult;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import java.time.LocalDate;

public class RegistroController {
    @FXML
    private TextField txtUsername;
    
    @FXML
    private TextField txtEmail;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private PasswordField txtConfirmPassword;
    
    @FXML
    private DatePicker dpFechaNacimiento;
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtApellido;
    
    @FXML
    private Button btnRegistrar;
    
    @FXML
    private Hyperlink linkLogin;
    
    @FXML
    private VBox formContainer;

    private final UsuarioService usuarioService;
    private static final int EDAD_MINIMA = 13;

    public RegistroController() {
        this.usuarioService = new UsuarioServiceImpl(new UsuarioRepositoryImpl());
    }

    @FXML
    public void initialize() {
        configurarValidacionesEnTiempoReal();
        configurarDatePicker();
        configurarEventos();
    }

    private void configurarValidacionesEnTiempoReal() {
        // Validación de username
        txtUsername.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!ValidationUtil.isValidUsername(newVal)) {
                mostrarErrorEnCampo(txtUsername, "Usuario debe tener entre 3 y 20 caracteres");
            } else {
                limpiarErrorEnCampo(txtUsername);
            }
            validarFormulario();
        });

        // Validación de email
        txtEmail.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!ValidationUtil.isValidEmail(newVal)) {
                mostrarErrorEnCampo(txtEmail, "Email no válido");
            } else {
                limpiarErrorEnCampo(txtEmail);
            }
            validarFormulario();
        });

        // Validación de contraseña
        txtPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            ValidationResult result = ValidationUtil.validatePassword(newVal);
            if (!result.isValid()) {
                mostrarErrorEnCampo(txtPassword, result.getMessage());
            } else {
                limpiarErrorEnCampo(txtPassword);
            }
            validarConfirmacionPassword();
            validarFormulario();
        });

        // Validación de confirmación de contraseña
        txtConfirmPassword.textProperty().addListener((obs, oldVal, newVal) -> {
            validarConfirmacionPassword();
            validarFormulario();
        });

        // Validación de nombre y apellido
        txtNombre.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!ValidationUtil.isValidNombre(newVal)) {
                mostrarErrorEnCampo(txtNombre, "Nombre no válido");
            } else {
                limpiarErrorEnCampo(txtNombre);
            }
            validarFormulario();
        });

        txtApellido.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!ValidationUtil.isValidNombre(newVal)) {
                mostrarErrorEnCampo(txtApellido, "Apellido no válido");
            } else {
                limpiarErrorEnCampo(txtApellido);
            }
            validarFormulario();
        });
    }

    private void configurarDatePicker() {
        dpFechaNacimiento.setValue(LocalDate.now().minusYears(EDAD_MINIMA));
        dpFechaNacimiento.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(LocalDate.now().minusYears(EDAD_MINIMA)));
            }
        });
    }

    private void configurarEventos() {
        linkLogin.setOnAction(event -> redirectToLogin());
        btnRegistrar.setOnAction(event -> handleRegistro());
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
        usuario.setPassword(txtPassword.getText());
        usuario.setNombre(txtNombre.getText().trim());
        usuario.setApellido(txtApellido.getText().trim());
        usuario.setFechaNacimiento(dpFechaNacimiento.getValue());
        return usuario;
    }

    private void validarConfirmacionPassword() {
        if (!txtPassword.getText().equals(txtConfirmPassword.getText())) {
            mostrarErrorEnCampo(txtConfirmPassword, "Las contraseñas no coinciden");
        } else {
            limpiarErrorEnCampo(txtConfirmPassword);
        }
    }

    private boolean validarFormularioCompleto() {
        boolean isValid = true;
        isValid &= ValidationUtil.isValidUsername(txtUsername.getText());
        isValid &= ValidationUtil.isValidEmail(txtEmail.getText());
        isValid &= ValidationUtil.isValidPassword(txtPassword.getText());
        isValid &= txtPassword.getText().equals(txtConfirmPassword.getText());
        isValid &= ValidationUtil.isValidNombre(txtNombre.getText());
        isValid &= ValidationUtil.isValidNombre(txtApellido.getText());
        isValid &= ValidationUtil.isValidAge(dpFechaNacimiento.getValue(), EDAD_MINIMA);
        return isValid;
    }

    private void validarFormulario() {
        btnRegistrar.setDisable(!validarFormularioCompleto());
    }

    private void redirectToLogin() {
        try {
            MainController.cargarVista("login");
        } catch (Exception e) {
            mostrarError("Error al redirigir al login: " + e.getMessage());
        }
    }

    private void mostrarErrorEnCampo(TextField campo, String mensaje) {
        campo.setStyle("-fx-border-color: red;");
        campo.setTooltip(new Tooltip(mensaje));
    }

    private void limpiarErrorEnCampo(TextField campo) {
        campo.setStyle("");
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