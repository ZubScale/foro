package com.forum.controller;

import com.forum.model.Notificacion;
import com.forum.model.Usuario;
import com.forum.service.NotificacionService;
import com.forum.service.NotificacionServiceImpl;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class NotificacionesController {
    @FXML private TableView<Notificacion> tblNotificaciones;
    private final NotificacionService notificacionService = new NotificacionServiceImpl();

    @FXML
    private void initialize() {
        cargarNotificaciones();
    }

    // Método faltante añadido
    @FXML
    private void volverAlMain() {
        try {
            Stage stage = (Stage) tblNotificaciones.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarNotificaciones() {
        String usuarioId = SessionManager.getUsuarioActual().getId();
        tblNotificaciones.getItems().setAll(
                notificacionService.obtenerNotificacionesUsuario(usuarioId)
        );
    }

    @FXML
    private void handleMarcarLeido() {
        Notificacion seleccionada = tblNotificaciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            notificacionService.marcarComoLeida(seleccionada.getId());
            cargarNotificaciones();
        }
    }
}