package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.io.IOException;
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
        cargarVistaLocal("/view/notificaciones.fxml");
    }

    @FXML
    private void redirectToMensajes() {
        cargarVistaLocal("/view/mensajes.fxml");
    }

    @FXML
    private void redirectToPerfil() {
        cargarVistaLocal("/view/perfil_usuario.fxml");
    }

    @FXML
    private void redirectToComunidades() {
        cargarVistaLocal("/view/comunidades.fxml");
    }

    @FXML
    private void redirectToPosts() {
        cargarVistaLocal("/view/posts.fxml");
    }

    @FXML
    private void redirectToTags() {
        cargarVistaLocal("/view/tags.fxml");
    }

    @FXML
    private void redirectToCategorias() {
        cargarVistaLocal("/view/categorias.fxml");
    }

    @FXML
    private void handleCerrarSesion() {
        Optional<ButtonType> resultado = mostrarConfirmacion(
                "Cerrar Sesión",
                "¿Está seguro que desea cerrar sesión?"
        );

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            SessionManager.logout();
            cargarVistaLogin();
        }
    }

    public static void cargarVista(String fxmlPath) {
        try {
            if (instance != null && instance.contentArea != null) {
                FXMLLoader loader = new FXMLLoader(MainController.class.getResource(fxmlPath));
                Parent contenido = loader.load();
                instance.contentArea.getChildren().setAll(contenido);
            } else {
                // Si no hay instancia de MainController, cargar en nueva ventana
                FXMLLoader loader = new FXMLLoader(MainController.class.getResource(fxmlPath));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.centerOnScreen();
                stage.show();
            }
        } catch (IOException e) {
            mostrarError("Error al cargar la vista: " + e.getMessage());
        }
    }

    private void cargarVistaLocal(String rutaFxml) {
        try {
            Stage stage = (Stage) lvTendencias.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(rutaFxml));
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (IOException e) {
            mostrarError("Error al cargar la vista: " + e.getMessage());
        }
    }

    private void cargarVistaLogin() {
        try {
            Stage stage = (Stage) lvTendencias.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            stage.setScene(new Scene(root));
            stage.setMaximized(false);
            stage.centerOnScreen();
        } catch (IOException e) {
            mostrarError("Error al cargar el login: " + e.getMessage());
        }
    }

    private Optional<ButtonType> mostrarConfirmacion(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert.showAndWait();
    }

    private static void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static MainController getInstance() {
        return instance;
    }
}