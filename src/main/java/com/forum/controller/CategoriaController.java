package com.forum.controller;

import com.forum.model.Categoria;
import com.forum.service.CategoriaService;
import com.forum.service.CategoriaServiceImpl;
import com.forum.repository.CategoriaRepositoryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class CategoriaController {
    @FXML
    private TableView<Categoria> tblCategorias;

    @FXML
    private TableColumn<Categoria, String> colId;

    @FXML
    private TableColumn<Categoria, String> colNombre;

    private final CategoriaService categoriaService = new CategoriaServiceImpl(new CategoriaRepositoryImpl());
    private ObservableList<Categoria> categoriaList;

    @FXML
    private void initialize() {
        configurarTabla();
        cargarCategorias();
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    private void cargarCategorias() {
        categoriaList = FXCollections.observableArrayList(categoriaService.listarTodas());
        tblCategorias.setItems(categoriaList);
    }

    @FXML
    private void mostrarCrearCategoria() {
        // Lógica para mostrar formulario de creación
        mostrarAlerta("Crear Categoría", "Funcionalidad no implementada aún.");
    }

    @FXML
    private void editarCategoria() {
        Categoria seleccionada = tblCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una categoría para editar.");
            return;
        }
        // Lógica para edición
        mostrarAlerta("Editar Categoría", "Funcionalidad no implementada aún.");
    }

    @FXML
    private void eliminarCategoria() {
        Categoria seleccionada = tblCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una categoría para eliminar.");
            return;
        }
        categoriaService.eliminarCategoria(seleccionada.getId());
        categoriaList.remove(seleccionada);
    }

    @FXML
    private void volverAGestion() {
        MainController.cargarVista("main");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}