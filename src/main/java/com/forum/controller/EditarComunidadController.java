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
    @FXML private TextField txtNombre;
    @FXML private TextArea taDescripcion;
    @FXML private TextArea taReglas;
    @FXML private ComboBox<String> cbCategorias;

    private final ComunidadService comunidadService =
            new ComunidadServiceImpl(new ComunidadRepositoryImpl());

    @FXML
    private void guardarCambiosComunidad() {
        // Lógica para guardar cambios
        MainController.cargarVista("comunidades");
    }

    @FXML
    private void cancelarEdicion() {
        MainController.cargarVista("comunidad_detalle");
    }
}