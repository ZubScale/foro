package com.forum.controller;

import com.forum.model.Comunidad;
import com.forum.service.ComunidadService;
import com.forum.service.ComunidadServiceImpl;
import com.forum.repository.ComunidadRepositoryImpl;
import com.forum.util.JsonUtil;
import com.forum.util.SceneNavigator;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EditarComunidadController {
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

    private static final String COMUNIDADES_JSON_PATH = "data/comunidades.json";

    @FXML
    public void initialize() {
        Comunidad nueva = (Comunidad) SceneNavigator.getSharedData("nuevaComunidad");
        if (nueva != null) {
            agregarComunidad(nueva);
            SceneNavigator.clearSharedData();
        } else {
            cargarComunidades();
        }
    }

    private void cargarComunidades() {
        List<Comunidad> comunidades = JsonUtil.cargarDatos(COMUNIDADES_JSON_PATH, Comunidad.class);
        if (comunidades.isEmpty()) {
            comunidades = comunidadService.listarTodas();
            JsonUtil.guardarDatos(COMUNIDADES_JSON_PATH, comunidades);
        }
        tblComunidades.getItems().setAll(comunidades);
    }

    @FXML
    private void buscarComunidad() {
        String textoBusqueda = txtBuscarComunidad.getText().toLowerCase().trim();
        List<Comunidad> comunidades = JsonUtil.cargarDatos(COMUNIDADES_JSON_PATH, Comunidad.class);

        if (textoBusqueda.isEmpty()) {
            tblComunidades.getItems().setAll(comunidades);
        } else {
            tblComunidades.getItems().setAll(
                    comunidades.stream()
                            .filter(comunidad -> comunidad.getNombre() != null &&
                                    comunidad.getNombre().toLowerCase().contains(textoBusqueda))
                            .collect(Collectors.toList())
            );
        }
    }

    @FXML
    private void mostrarCrearPost() {
        SceneNavigator.navigateTo(SceneNavigator.CREAR_POST_VIEW);
    }

    @FXML
    private void editarComunidad() {
        Comunidad seleccionada = tblComunidades.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            SessionManager.login(SessionManager.getUsuarioActual());
            SceneNavigator.setSharedData("comunidadSeleccionada", seleccionada);
            SceneNavigator.navigateTo(SceneNavigator.EDITAR_COMUNIDAD_VIEW);
        } else {
            mostrarAlerta("Debe seleccionar una comunidad para editar.");
        }
    }

    @FXML
    private void volverAComunidades() {
        SceneNavigator.navigateTo(SceneNavigator.COMUNIDADES_VIEW);
    }

    @FXML
    private void redirectToCrearComunidad() {
        SceneNavigator.navigateTo(SceneNavigator.CREAR_COMUNIDAD_VIEW);
    }

    public void agregarComunidad(Comunidad comunidad) {
        List<Comunidad> comunidades = JsonUtil.cargarDatos(COMUNIDADES_JSON_PATH, Comunidad.class);
        comunidades.add(comunidad);
        JsonUtil.guardarDatos(COMUNIDADES_JSON_PATH, comunidades);
        cargarComunidades();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Información");
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}