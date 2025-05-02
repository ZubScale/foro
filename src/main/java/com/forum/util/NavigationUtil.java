package com.forum.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NavigationUtil {

    private static final Logger logger = Logger.getLogger(NavigationUtil.class.getName());

    // Método principal para cambiar de vista
    public static void cambiarVista(Stage stage, String fxmlPath) {
        try {
            Parent root = cargarFXML(fxmlPath);
            stage.setScene(new Scene(root));
            stage.sizeToScene();
        } catch (IOException e) {
            manejarError("Error al cargar la vista: " + fxmlPath, e);
        }
    }

    // Método sobrecargado para usar desde cualquier nodo
    public static void cambiarVista(Node node, String fxmlPath) {
        Stage stage = (Stage) node.getScene().getWindow();
        cambiarVista(stage, fxmlPath);
    }

    // Método para cargar vistas dentro de paneles
    public static void cargarEnPane(Pane contenedor, String fxmlPath) {
        try {
            contenedor.getChildren().clear();
            contenedor.getChildren().add(cargarFXML(fxmlPath));
        } catch (IOException e) {
            manejarError("Error al cargar panel: " + fxmlPath, e);
        }
    }

    // Cargar FXML con manejo centralizado
    private static Parent cargarFXML(String fxmlPath) throws IOException {
        return FXMLLoader.load(Objects.requireNonNull(
                NavigationUtil.class.getResource(fxmlPath)
        ));
    }

    // Manejo centralizado de errores
    private static void manejarError(String mensaje, Exception e) {
        logger.log(Level.SEVERE, mensaje, e);
        mostrarAlertaError(mensaje);
    }

    private static void mostrarAlertaError(String mensaje) {
        new Alert(Alert.AlertType.ERROR,
                "Error de navegación: " + mensaje + "\nConsulte los logs")
                .showAndWait();
    }

    // Método para modificar parámetros o configuraciones globales
    public static void setParametro(String clave, Object valor) {
        // Implementación para almacenar o manejar el parámetro según sea necesario
        // Nota: Ajustar lógica a las necesidades específicas del proyecto
    }
}