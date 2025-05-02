package com.forum.controller;

import com.forum.model.Comunidad;
import com.forum.service.ComunidadService;
import com.forum.service.ComunidadServiceImpl;
import com.forum.repository.ComunidadRepositoryImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ComunidadController {
    // Componentes para comunidades.fxml
    @FXML private TableView<Comunidad> tblComunidades;
    @FXML private TextField txtBuscarComunidad;

    // Componentes para comunidad_detalle.fxml
    @FXML private TableView<?> tblPosts;
    @FXML private ListView<?> lstTemas;

    private final ComunidadService comunidadService =
            new ComunidadServiceImpl(new ComunidadRepositoryImpl());

    @FXML
    public void initialize() {
        cargarComunidades();
    }

    private void cargarComunidades() {
        tblComunidades.getItems().setAll(comunidadService.listarTodas());
    }

    // Métodos para comunidad_detalle.fxml
    @FXML
    private void mostrarCrearPost() {
        MainController.cargarVista("crear_post");
    }

    @FXML
    private void editarComunidad() {
        Comunidad seleccionada = tblComunidades.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            MainController.cargarVista("editar_comunidad");
        }
    }

    @FXML
    private void volverAComunidades() {
        MainController.cargarVista("comunidades");
    }

    @FXML
    private void redirectToCrearComunidad() {
        MainController.cargarVista("crear_comunidad");
    }

    private void mostrarAlerta(String mensaje) {
        new Alert(Alert.AlertType.INFORMATION, mensaje).show();
    }
}