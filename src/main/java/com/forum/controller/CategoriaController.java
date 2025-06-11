package com.forum.controller;

import com.forum.model.Categoria;
import com.forum.service.CategoriaService;
import com.forum.service.CategoriaServiceImpl;
import com.forum.repository.CategoriaRepositoryImpl;
import com.forum.util.JsonUtil;
import com.forum.util.SceneNavigator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDateTime;
import java.util.List;

public class CategoriaController {
    @FXML
    private TableView<Categoria> tblCategorias;

    private final CategoriaService categoriaService = new CategoriaServiceImpl(new CategoriaRepositoryImpl());
    private ObservableList<Categoria> categoriaList;

    private static final String CATEGORIAS_JSON_PATH = "data/categorias.json";

    @FXML
    private void initialize() {
        configurarTabla();
        cargarCategorias();
    }

    private void configurarTabla() {
        TableColumn<Categoria, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colNombre.setPrefWidth(250);

        TableColumn<Categoria, String> colComunidades = new TableColumn<>("Comunidades Asociadas");
        colComunidades.setCellValueFactory(new PropertyValueFactory<>("comunidadesAsociadas"));
        colComunidades.setPrefWidth(300);

        TableColumn<Categoria, LocalDateTime> colFechaCreacion = new TableColumn<>("Fecha Creación");
        colFechaCreacion.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        colFechaCreacion.setPrefWidth(150);

        tblCategorias.getColumns().clear();
        tblCategorias.getColumns().addAll(colNombre, colComunidades, colFechaCreacion);

        tblCategorias.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);
    }

    private void cargarCategorias() {
        try {
            List<Categoria> cargadas = JsonUtil.cargarDatos(CATEGORIAS_JSON_PATH, Categoria.class);
            categoriaList = FXCollections.observableArrayList(cargadas);
            tblCategorias.setItems(categoriaList);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al cargar las categorías: " + e.getMessage());
        }
    }

    @FXML
    private void mostrarCrearCategoria() {
        try {
            SceneNavigator.navigateTo(SceneNavigator.CREAR_CATEGORIA_VIEW);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al abrir el formulario de creación: " + e.getMessage());
        }
    }

    @FXML
    private void editarCategoria() {
        Categoria seleccionada = tblCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una categoría para editar.");
            return;
        }

        try {
            SceneNavigator.setSharedData("categoriaSeleccionada", seleccionada);
            SceneNavigator.navigateTo(SceneNavigator.EDITAR_CATEGORIA_VIEW);
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al editar la categoría: " + e.getMessage());
        }
    }

    @FXML
    private void eliminarCategoria() {
        Categoria seleccionada = tblCategorias.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una categoría para eliminar.");
            return;
        }

        try {
            if (confirmarEliminacion()) {
                categoriaService.eliminarCategoria(seleccionada.getId());
                categoriaList.remove(seleccionada);
                JsonUtil.guardarDatos(CATEGORIAS_JSON_PATH, categoriaList);
                mostrarAlerta("Éxito", "Categoría eliminada correctamente.");
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al eliminar la categoría: " + e.getMessage());
        }
    }

    private boolean confirmarEliminacion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro que desea eliminar esta categoría?");
        alert.setContentText("Esta acción no se puede deshacer.");
        return alert.showAndWait().orElse(javafx.scene.control.ButtonType.CANCEL)
                == javafx.scene.control.ButtonType.OK;
    }

    @FXML
    private void volverAGestion() {
        try {
            SceneNavigator.navigateToMain();
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al volver a la pantalla principal: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert.AlertType tipo = titulo.startsWith("Error") ?
                Alert.AlertType.ERROR : Alert.AlertType.INFORMATION;
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
