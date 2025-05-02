package com.forum.controller;

import com.forum.model.Tag;
import com.forum.service.TagService;
import com.forum.service.TagServiceImpl;
import com.forum.repository.TagRepositoryImpl;
import com.forum.util.NavigationUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class TagController {
    @FXML
    private TableView<Tag> tblTags;

    @FXML
    private TableColumn<Tag, String> colNombre;

    @FXML
    private TableColumn<Tag, String> colDescripcion;

    @FXML
    private TableColumn<Tag, Integer> colPostsAsociados;

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
        colPostsAsociados.setCellValueFactory(new PropertyValueFactory<>("postsAsociados"));
    }

    private void cargarTags() {
        tagList = FXCollections.observableArrayList(tagService.obtenerTodos());
        tblTags.setItems(tagList);
    }

    @FXML
    private void mostrarCrearTag() {
        NavigationUtil.cambiarVista(tblTags, "/view/crear_tag.fxml");
    }

    @FXML
    private void editarTag() {
        Tag tagSeleccionado = tblTags.getSelectionModel().getSelectedItem();
        if (tagSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un tag para editar.");
            return;
        }
        NavigationUtil.setParametro("tagSeleccionado", tagSeleccionado);
        NavigationUtil.cambiarVista(tblTags, "/view/editar_tag.fxml");
    }

    @FXML
    private void eliminarTag() {
        Tag tagSeleccionado = tblTags.getSelectionModel().getSelectedItem();
        if (tagSeleccionado == null) {
            mostrarAlerta("Error", "Debe seleccionar un tag para eliminar.");
            return;
        }
        tagService.eliminar(tagSeleccionado.getId());
        tagList.remove(tagSeleccionado);
    }

    @FXML
    private void volverAGestion() {
        NavigationUtil.cambiarVista(tblTags, "/view/main.fxml");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
