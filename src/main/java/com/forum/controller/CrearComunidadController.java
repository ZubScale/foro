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
    @FXML private TextField txtNombre;
    @FXML private TextArea taDescripcion;
    @FXML private TextArea taReglas;
    @FXML private ComboBox<String> cbCategoria;
    @FXML private ComboBox<String> cbPrivacidad;

    private final ComunidadService comunidadService =
            new ComunidadServiceImpl(new ComunidadRepositoryImpl());

    @FXML
    private void crearComunidad() {
        Comunidad nueva = new Comunidad(
                txtNombre.getText(),
                SessionManager.getUsuarioActual().getId(),
                taDescripcion.getText(),
                taReglas.getText()
        );
        comunidadService.crearComunidad(nueva);
        MainController.cargarVista("comunidades");
    }
}