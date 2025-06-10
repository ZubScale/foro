package com.forum.controller;

import com.forum.model.Comunidad;
import com.forum.service.ComunidadService;
import com.forum.service.ComunidadServiceImpl;
import com.forum.repository.ComunidadRepositoryImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

public class EditarComunidadController {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextArea taDescripcion;

    @FXML
    private TextArea taReglas;

    @FXML
    private ComboBox<String> cbCategorias;

    private Comunidad comunidadActual;
    private final ComunidadService comunidadService = 
            new ComunidadServiceImpl(new ComunidadRepositoryImpl());

    @FXML
    public void initialize() {
        configurarCategorias();
        cargarDatosComunidad();
        configurarValidaciones();
    }

    private void configurarCategorias() {
        cbCategorias.setItems(FXCollections.observableArrayList(
            "Tecnología",
            "Programación",
            "Ciencia",
            "Gaming",
            "Arte",
            "Música",
            "Deportes",
            "Otros"
        ));
    }

    private void cargarDatosComunidad() {
        try {
            // Obtener la comunidad seleccionada de la sesión
            comunidadActual = SessionManager.getComunidadActual();
            if (comunidadActual == null) {
                mostrarError("No se encontró la comunidad a editar");
                cancelarEdicion();
                return;
            }

            // Cargar los datos en los campos
            txtNombre.setText(comunidadActual.getNombre());
            taDescripcion.setText(comunidadActual.getDescripcion());
            taReglas.setText(comunidadActual.getReglas());
            cbCategorias.setValue(comunidadActual.getCategoria());

            // Verificar permisos
            if (!tienePermisosEdicion()) {
                deshabilitarEdicion();
                mostrarError("No tienes permisos para editar esta comunidad");
                cancelarEdicion();
            }

        } catch (Exception e) {
            mostrarError("Error al cargar los datos de la comunidad: " + e.getMessage());
            cancelarEdicion();
        }
    }

    private void configurarValidaciones() {
        // Validación del nombre
        txtNombre.textProperty().addListener((obs, oldVal, newVal) -> {
            validarFormulario();
        });

        // Validación de la descripción
        taDescripcion.textProperty().addListener((obs, oldVal, newVal) -> {
            validarFormulario();
        });
    }

    @FXML
    private void guardarCambiosComunidad() {
        if (!validarFormulario()) {
            mostrarError("Por favor, complete todos los campos requeridos correctamente");
            return;
        }

        try {
            // Actualizar datos de la comunidad
            comunidadActual.setNombre(txtNombre.getText().trim());
            comunidadActual.setDescripcion(taDescripcion.getText().trim());
            comunidadActual.setReglas(taReglas.getText().trim());
            comunidadActual.setCategoria(cbCategorias.getValue());

            // Guardar cambios
            comunidadService.actualizarComunidad(comunidadActual);
            
            mostrarExito("Cambios guardados exitosamente");
            volverADetalleComunidad();

        } catch (Exception e) {
            mostrarError("Error al guardar los cambios: " + e.getMessage());
        }
    }

    @FXML
    private void cancelarEdicion() {
        volverADetalleComunidad();
    }

    private boolean validarFormulario() {
        if (txtNombre.getText() == null || txtNombre.getText().trim().isEmpty()) {
            return false;
        }
        if (taDescripcion.getText() == null || taDescripcion.getText().trim().isEmpty()) {
            return false;
        }
        if (cbCategorias.getValue() == null) {
            return false;
        }
        return true;
    }

    private boolean tienePermisosEdicion() {
        // Verificar si el usuario actual es el creador o moderador
        return SessionManager.getUsuarioActual() != null &&
               (SessionManager.getUsuarioActual().getId().equals(comunidadActual.getCreadorId()) ||
                comunidadActual.getModeradores().contains(SessionManager.getUsuarioActual().getId()));
    }

    private void deshabilitarEdicion() {
        txtNombre.setDisable(true);
        taDescripcion.setDisable(true);
        taReglas.setDisable(true);
        cbCategorias.setDisable(true);
    }

    private void volverADetalleComunidad() {
        try {
            MainController.cargarVista("comunidad_detalle");
        } catch (Exception e) {
            mostrarError("Error al volver al detalle de la comunidad: " + e.getMessage());
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