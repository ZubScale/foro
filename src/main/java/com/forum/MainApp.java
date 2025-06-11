package com.forum;

import com.forum.util.PreferencesUtil;
import com.forum.util.SceneNavigator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.logging.Logger;

public class MainApp extends Application {
    private static final Logger logger = Logger.getLogger(MainApp.class.getName());

    @Override
    public void start(Stage primaryStage) {
        try {
            // Configurar el SceneNavigator
            SceneNavigator.setPrimaryStage(primaryStage);

            // Configurar propiedades de la ventana
            primaryStage.setTitle("ForoApp");

            // Intentar cargar ícono de la aplicación (opcional)
            try {
                primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/app-icon.png")));
            } catch (Exception e) {
                // Si no hay ícono, continuar sin él
                logger.info("No se encontró ícono de la aplicación");
            }

            // Restaurar tamaño y posición de ventana desde preferencias
            if (!PreferencesUtil.isFirstRun()) {
                primaryStage.setX(PreferencesUtil.getWindowX());
                primaryStage.setY(PreferencesUtil.getWindowY());
                primaryStage.setWidth(PreferencesUtil.getWindowWidth());
                primaryStage.setHeight(PreferencesUtil.getWindowHeight());
                primaryStage.setMaximized(PreferencesUtil.isWindowMaximized());
            } else {
                // Primera ejecución - configurar tamaño por defecto
                primaryStage.setWidth(1024);
                primaryStage.setHeight(768);
                primaryStage.centerOnScreen();
            }

            // Guardar tamaño y posición al cerrar
            primaryStage.setOnCloseRequest(event -> {
                if (!primaryStage.isMaximized()) {
                    PreferencesUtil.setWindowX(primaryStage.getX());
                    PreferencesUtil.setWindowY(primaryStage.getY());
                    PreferencesUtil.setWindowWidth(primaryStage.getWidth());
                    PreferencesUtil.setWindowHeight(primaryStage.getHeight());
                }
                PreferencesUtil.setWindowMaximized(primaryStage.isMaximized());
            });

            // Cargar vista inicial (login)
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root);

            // Aplicar tema si está configurado
            applyTheme(scene);

            primaryStage.setScene(scene);
            primaryStage.show();

            logger.info("Aplicación iniciada exitosamente");

        } catch (Exception e) {
            logger.severe("Error al iniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void applyTheme(Scene scene) {
        PreferencesUtil.Theme theme = PreferencesUtil.getTheme();

        // Aquí podrías aplicar diferentes hojas de estilo según el tema
        switch (theme) {
            case DARK:
                // scene.getStylesheets().add(getClass().getResource("/styles/dark-theme.css").toExternalForm());
                break;
            case LIGHT:
                // scene.getStylesheets().add(getClass().getResource("/styles/light-theme.css").toExternalForm());
                break;
            case AUTO:
                // Detectar tema del sistema y aplicar
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}