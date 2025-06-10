package com.forum.controller;

import com.forum.model.Comunidad;
import com.forum.service.ComunidadService;
import com.forum.service.ComunidadServiceImpl;
import com.forum.repository.ComunidadRepositoryImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

public class CrearComunidadController {
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextArea taDescripcion;
    
    @FXML
    private TextArea taReglas;
    
    @FXML
    private ComboBox<String> cbCategoria;
    
    @FXML
    private ComboBox<String> cbPrivacidad;

    private final ComunidadService comunidadService = 
            new ComunidadServiceImpl(new ComunidadRepositoryImpl());

    @FXML
    public void initialize() {
        configurarCombos();
        configurarValidaciones();
    }

    private void configurarCombos() {
        // Configurar categorías disponibles
        cbCategoria.setItems(FXCollections.observableArrayList(
            "Tecnología",
            "Programación",
            "Ciencia",
            "Gaming",
            "Arte",
            "Música",
            "Deportes",
            "Otros"
        ));

        // Configurar opciones de privacidad
        cbPrivacidad.setItems(FXCollections.observableArrayList(
            "Pública",
            "Privada",
            "Restringida"
        ));

        // Establecer valores por defecto
        cbCategoria.setValue("Otros");
        cbPrivacidad.setValue("Pública");
    }

    private void configurarValidaciones() {
        // Validación del nombre
        txtNombre.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                validarFormulario();
            }
        });

        // Validación de la descripción
        taDescripcion.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                validarFormulario();
            }
        });
    }

    @FXML
    private void crearComunidad() {
        if (!validarFormulario()) {
            mostrarError("Por favor, complete todos los campos requeridos");
            return;
        }

        try {
            Comunidad nuevaComunidad = new Comunidad();
            nuevaComunidad.setNombre(txtNombre.getText().trim());
            nuevaComunidad.setDescripcion(taDescripcion.getText().trim());
            nuevaComunidad.setReglas(taReglas.getText().trim());
            nuevaComunidad.setCategoria(cbCategoria.getValue());
            nuevaComunidad.setPrivacidad(cbPrivacidad.getValue());
            nuevaComunidad.setCreadorId(SessionManager.getUsuarioActual().getId());

            comunidadService.crearComunidad(nuevaComunidad);
            mostrarExito("Comunidad creada exitosamente");
            volverAListaComunidades();

        } catch (Exception e) {
            mostrarError("Error al crear la comunidad: " + e.getMessage());
        }
    }

    private boolean validarFormulario() {
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            return false;
        }
        if (taDescripcion.getText() == null || taDescripcion.getText().trim().isEmpty()) {
            return false;
        }
        if (cbCategoria.getValue() == null) {
            return false;
        }
        if (cbPrivacidad.getValue() == null) {
            return false;
        }
        return true;
    }

    private void volverAListaComunidades() {
        try {
            MainController.cargarVista("comunidades");
        } catch (Exception e) {
            mostrarError("Error al volver a la lista de comunidades: " + e.getMessage());
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