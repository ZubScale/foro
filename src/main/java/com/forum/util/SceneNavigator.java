package com.forum.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilidad centralizada para la navegación entre vistas
 */
public class SceneNavigator {
    private static final Logger logger = Logger.getLogger(SceneNavigator.class.getName());
    private static Stage primaryStage;
    private static Scene mainScene;
    private static final Map<String, Object> sharedData = new HashMap<>();

    // Rutas de las vistas
    public static final String LOGIN_VIEW = "/view/login.fxml";
    public static final String MAIN_VIEW = "/view/main.fxml";
    public static final String REGISTRO_VIEW = "/view/registro.fxml";
    public static final String RECUPERAR_PASSWORD_VIEW = "/view/recuperar_password.fxml";
    public static final String PERFIL_VIEW = "/view/perfil_usuario.fxml";
    public static final String COMUNIDADES_VIEW = "/view/comunidades.fxml";
    public static final String CREAR_COMUNIDAD_VIEW = "/view/crear_comunidad.fxml";
    public static final String EDITAR_COMUNIDAD_VIEW = "/view/editar_comunidad.fxml";
    public static final String COMUNIDAD_DETALLE_VIEW = "/view/comunidad_detalle.fxml";
    public static final String POSTS_VIEW = "/view/posts.fxml";
    public static final String POST_DETALLE_VIEW = "/view/post_detalle.fxml";
    public static final String CREAR_POST_VIEW = "/view/crear_post.fxml";
    public static final String EDITAR_POST_VIEW = "/view/editar_post.fxml";
    public static final String MENSAJES_VIEW = "/view/mensajes.fxml";
    public static final String NOTIFICACIONES_VIEW = "/view/notificaciones.fxml";
    public static final String TAGS_VIEW = "/view/tags.fxml";
    public static final String CATEGORIAS_VIEW = "/view/categorias.fxml";
    public static final String CREAR_CATEGORIA_VIEW = "/view/crear_categoria.fxml";
    public static final String EDITAR_CATEGORIA_VIEW = "/view/editar_categoria.fxml";

    /**
     * Establece el stage principal de la aplicación
     */
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    /**
     * Establece la escena principal
     */
    public static void setMainScene(Scene scene) {
        mainScene = scene;
    }

    /**
     * Navega a una vista reemplazando la escena actual
     */
    public static void navigateTo(String fxmlPath) {
        navigateTo(fxmlPath, false);
    }

    /**
     * Navega a una vista con opción de maximizar
     */
    public static void navigateTo(String fxmlPath, boolean maximize) {
        try {
            Parent root = loadFXML(fxmlPath);
            Scene scene = new Scene(root);

            if (primaryStage != null) {
                // Guardar posición y tamaño actual si no está maximizada
                if (!primaryStage.isMaximized()) {
                    PreferencesUtil.setWindowX(primaryStage.getX());
                    PreferencesUtil.setWindowY(primaryStage.getY());
                    PreferencesUtil.setWindowWidth(primaryStage.getWidth());
                    PreferencesUtil.setWindowHeight(primaryStage.getHeight());
                }

                primaryStage.setScene(scene);
                primaryStage.setMaximized(maximize);

                if (!maximize) {
                    primaryStage.sizeToScene();
                    primaryStage.centerOnScreen();
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al navegar a: " + fxmlPath, e);
            mostrarError("Error al cargar la vista", "No se pudo cargar: " + fxmlPath);
        }
    }

    /**
     * Navega desde cualquier nodo (obtiene el Stage del nodo)
     */
    public static void navigateFrom(Node node, String fxmlPath) {
        Stage stage = (Stage) node.getScene().getWindow();
        if (primaryStage == null) {
            primaryStage = stage;
        }
        navigateTo(fxmlPath);
    }

    /**
     * Carga una vista dentro de un contenedor (para vistas anidadas)
     */
    public static void loadViewIntoContainer(Pane container, String fxmlPath) {
        try {
            Parent view = loadFXML(fxmlPath);
            container.getChildren().clear();
            container.getChildren().add(view);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar vista en contenedor: " + fxmlPath, e);
            mostrarError("Error al cargar vista", "No se pudo cargar la vista en el contenedor");
        }
    }

    /**
     * Abre una vista en una nueva ventana
     */
    public static Stage openInNewWindow(String fxmlPath, String title) {
        return openInNewWindow(fxmlPath, title, false);
    }

    /**
     * Abre una vista en una nueva ventana con opción modal
     */
    public static Stage openInNewWindow(String fxmlPath, String title, boolean modal) {
        try {
            Parent root = loadFXML(fxmlPath);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            if (modal && primaryStage != null) {
                stage.initOwner(primaryStage);
                stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            }

            stage.centerOnScreen();
            stage.show();
            return stage;

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error al abrir nueva ventana: " + fxmlPath, e);
            mostrarError("Error", "No se pudo abrir la ventana");
            return null;
        }
    }

    /**
     * Carga un FXML y retorna el Parent
     */
    private static Parent loadFXML(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
        return loader.load();
    }

    /**
     * Carga un FXML y retorna tanto el Parent como el controlador
     */
    public static <T> LoadResult<T> loadFXMLWithController(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneNavigator.class.getResource(fxmlPath));
        Parent root = loader.load();
        T controller = loader.getController();
        return new LoadResult<>(root, controller);
    }

    /**
     * Guarda datos compartidos entre vistas
     */
    public static void setSharedData(String key, Object value) {
        sharedData.put(key, value);
    }

    /**
     * Obtiene datos compartidos entre vistas
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSharedData(String key) {
        return (T) sharedData.get(key);
    }

    /**
     * Limpia los datos compartidos
     */
    public static void clearSharedData() {
        sharedData.clear();
    }

    /**
     * Elimina un dato compartido específico
     */
    public static void removeSharedData(String key) {
        sharedData.remove(key);
    }

    /**
     * Navega al login (útil para logout)
     */
    public static void navigateToLogin() {
        clearSharedData();
        navigateTo(LOGIN_VIEW, false);
    }

    /**
     * Navega al dashboard principal
     */
    public static void navigateToMain() {
        navigateTo(MAIN_VIEW, true);
    }

    /**
     * Muestra un diálogo de error
     */
    private static void mostrarError(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Clase para retornar tanto el Parent como el Controller
     */
    public static class LoadResult<T> {
        private final Parent parent;
        private final T controller;

        public LoadResult(Parent parent, T controller) {
            this.parent = parent;
            this.controller = controller;
        }

        public Parent getParent() {
            return parent;
        }

        public T getController() {
            return controller;
        }
    }

    // Constructor privado para evitar instanciación
    private SceneNavigator() {
        throw new AssertionError("SceneNavigator no debe ser instanciada");
    }
}