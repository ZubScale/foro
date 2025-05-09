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

import java.util.stream.Collectors;

public class ComunidadController {
    // Componentes para comunidades.fxml
    @FXML
    private TableView<Comunidad> tblComunidades;
    @FXML
    private TextField txtBuscarComunidad;

    // Componentes para comunidad_detalle.fxml
    @FXML
    private TableView<?> tblPosts;
    @FXML
    private ListView<?> lstTemas;

    private final ComunidadService comunidadService =
            new ComunidadServiceImpl(new ComunidadRepositoryImpl());

    @FXML
    public void initialize() {
        cargarComunidades();
    }

    private void cargarComunidades() {
        tblComunidades.getItems().setAll(comunidadService.listarTodas());
    }

    @FXML
    private void buscarComunidad() {
        String textoBusqueda = txtBuscarComunidad.getText().toLowerCase().trim();
        if (textoBusqueda.isEmpty()) {
            cargarComunidades();
        } else {
            tblComunidades.getItems().setAll(
                    comunidadService.listarTodas().stream()
                            .filter(comunidad -> comunidad.getNombre() != null &&
                                    comunidad.getNombre().toLowerCase().contains(textoBusqueda))
                            .collect(Collectors.toList())
            );
        }
    }

    @FXML
    private void mostrarCrearPost() {
        MainController.cargarVista("crear_post");
    }

    @FXML
    private void editarComunidad() {
        Comunidad seleccionada = tblComunidades.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Guardar la comunidad seleccionada en una sesión o pasarla a la vista siguiente
            SessionManager.login(SessionManager.getUsuarioActual());
            MainController.cargarVista("editar_comunidad");
        } else {
            mostrarAlerta("Debe seleccionar una comunidad para editar.");
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
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}