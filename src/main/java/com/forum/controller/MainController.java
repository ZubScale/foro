package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.util.SessionManager;
import com.forum.util.SceneNavigator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import java.util.Optional;

public class MainController {
    @FXML
    private Label lblUsuarioActual;

    @FXML
    private ListView<String> lvTendencias;

    @FXML
    private VBox vboxFeed;

    private Usuario usuarioActual;
    private static MainController instance;

    @FXML
    public void initialize() {
        instance = this;
        cargarUsuarioActual();
        cargarTendencias();
        cargarFeed();
    }

    private void cargarUsuarioActual() {
        usuarioActual = SessionManager.getUsuarioActual();
        if (usuarioActual != null) {
            // Mostrar nombre del usuario si existe un label
            if (lblUsuarioActual != null) {
                lblUsuarioActual.setText("Bienvenido, " + usuarioActual.getUsername());
            }
        }
    }

    private void cargarTendencias() {
        if (lvTendencias != null) {
            lvTendencias.getItems().addAll(
                    "#JavaProgramming",
                    "#SpringBoot",
                    "#WebDevelopment",
                    "#ArtificialIntelligence",
                    "#CloudComputing"
            );
        }
    }

    private void cargarFeed() {
        // Por ahora, cargar posts de ejemplo
        if (vboxFeed != null) {
            Label titulo = new Label("Bienvenido al Foro");
            titulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label contenido = new Label("Aquí aparecerán los posts más recientes de las comunidades que sigues.");
            contenido.setWrapText(true);

            vboxFeed.getChildren().addAll(titulo, contenido);
        }
    }

    @FXML
    private void redirectToNotificaciones() {
        SceneNavigator.navigateFrom(lvTendencias, SceneNavigator.NOTIFICACIONES_VIEW);
    }

    @FXML
    private void redirectToMensajes() {
        SceneNavigator.navigateFrom(lvTendencias, SceneNavigator.MENSAJES_VIEW);
    }

    @FXML
    private void redirectToPerfil() {
        SceneNavigator.navigateFrom(lvTendencias, SceneNavigator.PERFIL_VIEW);
    }

    @FXML
    private void redirectToComunidades() {
        SceneNavigator.navigateFrom(lvTendencias, SceneNavigator.COMUNIDADES_VIEW);
    }

    @FXML
    private void redirectToPosts() {
        SceneNavigator.navigateFrom(lvTendencias, SceneNavigator.POSTS_VIEW);
    }

    @FXML
    private void redirectToTags() {
        SceneNavigator.navigateFrom(lvTendencias, SceneNavigator.TAGS_VIEW);
    }

    @FXML
    private void redirectToCategorias() {
        SceneNavigator.navigateFrom(lvTendencias, SceneNavigator.CATEGORIAS_VIEW);
    }

    @FXML
    private void handleCerrarSesion() {
        Optional<ButtonType> resultado = mostrarConfirmacion(
                "Cerrar Sesión",
                "¿Está seguro que desea cerrar sesión?"
        );

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            SessionManager.logout();
            SceneNavigator.navigateToLogin();
        }
    }

    private Optional<ButtonType> mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}