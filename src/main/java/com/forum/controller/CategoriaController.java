package com.forum.controller;

import com.forum.model.Categoria;
import com.forum.service.CategoriaService;
import com.forum.service.CategoriaServiceImpl;
import com.forum.repository.CategoriaRepositoryImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class CategoriaController {
    @FXML
    private TableView<Categoria> tblCategorias;

    private final CategoriaService categoriaService =
            new CategoriaServiceImpl(new CategoriaRepositoryImpl());

    @FXML
    private void initialize() {
        tblCategorias.getItems().setAll(categoriaService.listarTodas());
    }

    @FXML
    private void mostrarCrearCategoria() {
        // Lógica para mostrar formulario de creación
    }

    @FXML
    private void editarCategoria() {
        Categoria seleccionada = tblCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            // Lógica para editar
        }
    }

    @FXML
    private void eliminarCategoria() {
        Categoria seleccionada = tblCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            categoriaService.eliminarCategoria(seleccionada.getId());
            initialize(); // Refrescar tabla
        }
    }

    @FXML
    private void volverAGestion() {
        // Ejemplo usando MainController para navegar
        MainController.cargarVista("main");
    }
}