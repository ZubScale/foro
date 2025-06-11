package com.forum.controller;

import com.forum.model.Notificacion;
import com.forum.util.JsonUtil;
import com.forum.util.SceneNavigator;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableRow;
import javafx.scene.input.MouseButton;

import java.util.List;
import java.util.stream.Collectors;

public class NotificacionesController {
    @FXML
    private TableView<Notificacion> tblNotificaciones;

    private static final String NOTIFICACIONES_JSON_PATH = "data/notificaciones.json";

    @FXML
    public void initialize() {
        cargarNotificaciones();
        configurarClickFila();
    }

    private void cargarNotificaciones() {
        try {
            String usuarioId = SessionManager.getUsuarioActual().getId();
            List<Notificacion> todas = JsonUtil.cargarDatos(NOTIFICACIONES_JSON_PATH, Notificacion.class);

            // Filtrar notificaciones del usuario
            List<Notificacion> usuarioNotificaciones = todas.stream()
                    .filter(n -> n.getId().equals(usuarioId))
                    .collect(Collectors.toList());

            tblNotificaciones.getItems().setAll(usuarioNotificaciones);
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudieron cargar las notificaciones.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    private void configurarClickFila() {
        tblNotificaciones.setRowFactory(tv -> {
            TableRow<Notificacion> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                    marcarComoLeida(row.getItem());
                }
            });
            return row;
        });
    }

    @FXML
    private void handleMarcarLeido() {
        Notificacion seleccionada = tblNotificaciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            marcarComoLeida(seleccionada);
        } else {
            mostrarAlerta("Advertencia", "Seleccione una notificación para marcarla como leída.", Alert.AlertType.WARNING);
        }
    }

    private void marcarComoLeida(Notificacion notificacion) {
        try {
            List<Notificacion> todas = JsonUtil.cargarDatos(NOTIFICACIONES_JSON_PATH, Notificacion.class);

            // Encontrar la notificación y marcarla como leída
            for (Notificacion n : todas) {
                if (n.getId().equals(notificacion.getId())) {
                    n.setLeida(true);
                    break;
                }
            }

            JsonUtil.guardarDatos(NOTIFICACIONES_JSON_PATH, todas);
            cargarNotificaciones();
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo marcar la notificación como leída.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }

    @FXML
    private void volverAlMain() {
        SceneNavigator.navigateTo(SceneNavigator.MAIN_VIEW);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
