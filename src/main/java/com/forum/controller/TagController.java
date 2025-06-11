package com.forum.controller;

import com.forum.model.Tag;
import com.forum.repository.TagRepositoryImpl;
import com.forum.service.TagService;
import com.forum.service.TagServiceImpl;
import com.forum.util.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class TagController {

    @FXML private TableView<Tag> tblTags;
    @FXML private TableColumn<Tag, String> colNombre;
    @FXML private TableColumn<Tag, String> colDescripcion;
    @FXML private TableColumn<Tag, Integer> colPostsAsociados;

    private final TagService tagService = new TagServiceImpl(new TagRepositoryImpl());
    private ObservableList<Tag> tagList;

    @FXML
    private void initialize() {
        configurarTabla();
        cargarTags();
    }

    private void configurarTabla() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPostsAsociados.setCellValueFactory(new PropertyValueFactory<>("contadorUso"));
    }

    private void cargarTags() {
        try {
            tagList = FXCollections.observableArrayList(tagService.listarTodosLosTags());
            tblTags.setItems(tagList);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar los tags.");
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarCrearTag() {
        NavigationUtil.cambiarVista(tblTags, "/view/crear_tag.fxml");
    }

    @FXML
    private void editarTag() {
        Tag tag = obtenerTagSeleccionado();
        if (tag == null) return;

        NavigationUtil.setParametro("tagSeleccionado", tag);
        NavigationUtil.cambiarVista(tblTags, "/view/editar_tag.fxml");
    }

    @FXML
    private void eliminarTag() {
        Tag tag = obtenerTagSeleccionado();
        if (tag == null) return;

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Estás seguro de que deseas eliminar este tag?");

        confirmacion.showAndWait().ifPresent(respuesta -> {
            if (respuesta == ButtonType.OK) {
                tagService.eliminarTag(tag.getId());
                tagList.remove(tag);
            }
        });
    }

    @FXML
    private void volverAGestion() {
        NavigationUtil.cambiarVista(tblTags, "/view/main.fxml");
    }

    private Tag obtenerTagSeleccionado() {
        Tag seleccionado = tblTags.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Advertencia", "Debes seleccionar un tag.");
        }
        return seleccionado;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
