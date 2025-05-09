package com.forum.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainController {
    @FXML
    private static BorderPane mainContainer;

    static void cargarVista(String fxml) {
        try {
            mainContainer.setCenter(
                    FXMLLoader.load(MainController.class.getResource("/view/" + fxml + ".fxml"))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void redirectToNotificaciones() {
        cargarVista("notificaciones");
    }

    @FXML
    private void redirectToMensajes() {
        cargarVista("mensajes");
    }

    @FXML
    private void redirectToPerfil() {
        cargarVista("perfil_usuario");
    }
}