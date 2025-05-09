package com.forum.controller;

import com.forum.model.Comunidad;
import com.forum.service.ComunidadService;
import com.forum.service.ComunidadServiceImpl;
import com.forum.repository.ComunidadRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditarComunidadController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private TextArea taReglas;
    @FXML
    private ComboBox<String> cbCategorias;

    private final ComunidadService comunidadService =
            new ComunidadServiceImpl(new ComunidadRepositoryImpl());

    @FXML
    private void guardarCambiosComunidad() {
        String nombre = txtNombre.getText().trim();
        String descripcion = taDescripcion.getText().trim();
        String idComunidad = "comunidad-id-placeholder"; // This should be dynamically fetched or passed
        Comunidad comunidad = comunidadService.obtenerPorId(idComunidad);

        if (comunidad != null) {
            comunidad.setNombre(nombre);
            comunidad.setDescripcion(descripcion);
            comunidadService.actualizarComunidad(comunidad);
            MainController.cargarVista("comunidades");
        } else {
            System.err.println("Error: Comunidad no encontrada");
        }
    }

    @FXML
    private void cancelarEdicion() {
        MainController.cargarVista("comunidad_detalle");
    }
}