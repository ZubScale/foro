package com.forum.controller;

import com.forum.model.Notificacion;
import com.forum.service.NotificacionService;
import com.forum.service.NotificacionServiceImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class NotificacionesController {
    @FXML
    private TableView<Notificacion> tblNotificaciones;
    private final NotificacionService notificacionService = new NotificacionServiceImpl();

    @FXML
    private void initialize() {
        cargarNotificaciones();
    }

    @FXML
    private void volverAlMain() {
        try {
            Stage stage = (Stage) tblNotificaciones.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo regresar al menú principal.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void cargarNotificaciones() {
        try {
            String usuarioId = SessionManager.getUsuarioActual().getId();
            tblNotificaciones.getItems().setAll(
                    notificacionService.obtenerNotificacionesUsuario(usuarioId)
            );
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar las notificaciones.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void handleMarcarLeido() {
        try {
            Notificacion seleccionada = tblNotificaciones.getSelectionModel().getSelectedItem();
            if (seleccionada != null) {
                notificacionService.marcarComoLeida(seleccionada.getId());
                cargarNotificaciones();
            } else {
                mostrarAlerta("Advertencia", "Seleccione una notificación para marcarla como leída.", Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo marcar la notificación como leída.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}