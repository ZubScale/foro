package com.forum.controller;

import com.forum.model.Comunidad;
import com.forum.service.ComunidadService;
import com.forum.service.ComunidadServiceImpl;
import com.forum.repository.ComunidadRepositoryImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CrearComunidadController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private ComboBox<String> cbCategoria;
    @FXML
    private ComboBox<String> cbPrivacidad;

    private final ComunidadService comunidadService =
            new ComunidadServiceImpl(new ComunidadRepositoryImpl());

    @FXML
    private void crearComunidad() {
        String nombre = txtNombre.getText().trim();
        String descripcion = taDescripcion.getText().trim();
        String creadorId = SessionManager.getUsuarioActual().getId();

        if (nombre.isEmpty() || descripcion.isEmpty() || cbCategoria.getValue() == null || cbPrivacidad.getValue() == null) {
            // Handle error (e.g., show an alert to the user)
            return;
        }

        Comunidad nueva = new Comunidad(null, nombre, descripcion, creadorId);
        nueva.getTags().add(cbCategoria.getValue()); // Add category as a tag
        nueva.setDescripcion(descripcion);
        comunidadService.crearComunidad(nueva);

        MainController.cargarVista("comunidades");
    }
}