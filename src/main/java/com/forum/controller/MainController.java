package com.forum.controller;

import com.forum.model.Usuario;
import com.forum.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.io.IOException;
import java.util.Optional;

public class MainController {
    @FXML
    private BorderPane mainContainer;
    
    @FXML
    private MenuBar menuPrincipal;
    
    @FXML
    private StackPane contentArea;
    
    @FXML
    private Label lblUsuarioActual;
    
    @FXML
    private Button btnPerfil;
    
    @FXML
    private Button btnNotificaciones;
    
    @FXML
    private Label lblNotificaciones;

    private static Stage primaryStage;
    private Usuario usuarioActual;
    private static MainController instance;

    public MainController() {
        instance = this;
    }

    @FXML
    public void initialize() {
        configurarMenus();
        cargarUsuarioActual();
        configurarNotificaciones();
        cargarVistaInicial();
    }

    private void configurarMenus() {
        // Menú Archivo
        Menu menuArchivo = new Menu("Archivo");
        MenuItem itemInicio = new MenuItem("Inicio");
        MenuItem itemSalir = new MenuItem("Salir");
        
        itemInicio.setOnAction(e -> cargarVistaPrincipal());
        itemSalir.setOnAction(e -> handleCerrarAplicacion());
        
        menuArchivo.getItems().addAll(itemInicio, new SeparatorMenuItem(), itemSalir);

        // Menú Usuario
        Menu menuUsuario = new Menu("Usuario");
        MenuItem itemPerfil = new MenuItem("Mi Perfil");
        MenuItem itemPreferencias = new MenuItem("Preferencias");
        MenuItem itemCerrarSesion = new MenuItem("Cerrar Sesión");
        
        itemPerfil.setOnAction(e -> cargarPerfil());
        itemPreferencias.setOnAction(e -> cargarPreferencias());
        itemCerrarSesion.setOnAction(e -> handleCerrarSesion());
        
        menuUsuario.getItems().addAll(itemPerfil, itemPreferencias, new SeparatorMenuItem(), itemCerrarSesion);

        // Menú Ayuda
        Menu menuAyuda = new Menu("Ayuda");
        MenuItem itemAcercaDe = new MenuItem("Acerca de");
        MenuItem itemAyuda = new MenuItem("Ver Ayuda");
        
        itemAcercaDe.setOnAction(e -> mostrarAcercaDe());
        itemAyuda.setOnAction(e -> mostrarAyuda());
        
        menuAyuda.getItems().addAll(itemAyuda, itemAcercaDe);

        menuPrincipal.getMenus().addAll(menuArchivo, menuUsuario, menuAyuda);
    }

    private void cargarUsuarioActual() {
        usuarioActual = SessionManager.getUsuarioActual();
        if (usuarioActual != null) {
            lblUsuarioActual.setText("Bienvenido, " + usuarioActual.getNombre());
            actualizarPermisosUI();
        }
    }

    private void configurarNotificaciones() {
        actualizarContadorNotificaciones();
        btnNotificaciones.setOnAction(e -> mostrarNotificaciones());
    }

    private void actualizarContadorNotificaciones() {
        // Aquí implementarías la lógica para obtener el número de notificaciones
        int numeroNotificaciones = 0; // Obtener de servicio de notificaciones
        lblNotificaciones.setVisible(numeroNotificaciones > 0);
        lblNotificaciones.setText(String.valueOf(numeroNotificaciones));
    }

    public static void cargarVista(String vista) {
        try {
            FXMLLoader loader = new FXMLLoader(MainController.class.getResource("/fxml/" + vista + ".fxml"));
            Parent contenido = loader.load();
            if (instance != null && instance.contentArea != null) {
                instance.contentArea.getChildren().setAll(contenido);
            }
        } catch (IOException e) {
            mostrarError("Error al cargar la vista: " + e.getMessage());
        }
    }

    private void cargarVistaInicial() {
        cargarVista("dashboard");
    }

    private void cargarVistaPrincipal() {
        cargarVista("dashboard");
    }

    private void cargarPerfil() {
        cargarVista("perfil");
    }

    private void cargarPreferencias() {
        cargarVista("preferencias");
    }

    private void actualizarPermisosUI() {
        boolean esAdmin = usuarioActual != null && usuarioActual.isAdmin();
        // Configurar visibilidad de elementos según permisos
        menuPrincipal.getMenus().stream()
                    .flatMap(menu -> menu.getItems().stream())
                    .forEach(item -> {
                        if (item.getId() != null && item.getId().startsWith("admin_")) {
                            item.setVisible(esAdmin);
                        }
                    });
    }

    @FXML
    private void handleCerrarSesion() {
        Optional<ButtonType> resultado = mostrarConfirmacion(
            "Cerrar Sesión",
            "¿Está seguro que desea cerrar sesión?"
        );

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            SessionManager.logout();
            cargarVista("login");
        }
    }

    @FXML
    private void handleCerrarAplicacion() {
        Optional<ButtonType> resultado = mostrarConfirmacion(
            "Salir",
            "¿Está seguro que desea salir de la aplicación?"
        );

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    private void mostrarNotificaciones() {
        // Implementar lógica para mostrar panel de notificaciones
    }

    private void mostrarAcercaDe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca De");
        alert.setHeaderText(null);
        alert.setContentText("Información sobre la aplicación...");
        alert.showAndWait();
    }

    private void mostrarAyuda() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ayuda");
        alert.setHeaderText(null);
        alert.setContentText("Aquí irá la información de ayuda...");
        alert.showAndWait();
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

    public BorderPane getMainContainer() {
        return mainContainer;
    }

    public static MainController getInstance() {
        return instance;
    }

    public StackPane getContentArea() {
        return contentArea;
    }

    public void setContentArea(StackPane contentArea) {
        this.contentArea = contentArea;
    }
}