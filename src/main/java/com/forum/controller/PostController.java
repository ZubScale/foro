package com.forum.controller;

import com.forum.model.Comentario;
import com.forum.model.Post;
import com.forum.service.PostService;
import com.forum.service.PostServiceImpl;
import com.forum.repository.PostRepositoryImpl;
import com.forum.util.SessionManager;
import com.forum.util.NavigationUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class PostController {
    // Elementos para editar post
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextArea txtContenido;
    @FXML
    private ListView<String> lvTagsDisponibles;
    @FXML
    private ListView<String> lvTagsSeleccionados;

    // Elementos para vista detalle post
    @FXML
    private Text txtTituloPost;
    @FXML
    private Label lblAutor;
    @FXML
    private Label lblFecha;
    @FXML
    private Label lblVotos;
    @FXML
    private TextArea txtContenidoPost;
    @FXML
    private ListView<Comentario> lstComentarios;
    @FXML
    private TextArea txtNuevoComentario;

    private final PostService postService = new PostServiceImpl(new PostRepositoryImpl());

    @FXML
    private void initialize() {
        cargarTagsDisponibles();
        // Si estamos en vista detalle, cargar datos del post
        if (txtTituloPost != null) cargarDetallePost();
    }

    private void cargarDetallePost() {
        Post postActual = SessionManager.getPostActual();
        if (postActual != null) {
            txtTituloPost.setText(postActual.getTitulo());
            lblAutor.setText("Autor: " + postActual.getUsuarioId());
            lblFecha.setText("Fecha: " + postActual.getFecha().toString());
            lblVotos.setText("Votos: " + postActual.getVotos());
            txtContenidoPost.setText(postActual.getContenido());
            lstComentarios.getItems().setAll(postActual.getComentarios() != null ? postActual.getComentarios() : new ArrayList<>());
        }
    }

    // Métodos para editar post
    @FXML
    private void guardarPostEditado() {
        if (txtTitulo.getText().isBlank() || txtContenido.getText().isBlank()) {
            mostrarAlerta("Error", "El título y contenido no pueden estar vacíos.");
            return;
        }

        Post postActual = SessionManager.getPostActual();
        if (postActual != null) {
            postActual.setTitulo(txtTitulo.getText());
            postActual.setContenido(txtContenido.getText());
            postActual.setTags(new ArrayList<>(lvTagsSeleccionados.getItems()));
            postService.actualizarPost(postActual);
            NavigationUtil.cambiarVista(txtTitulo, "/view/main.fxml");
        } else {
            mostrarAlerta("Error", "No se pudo guardar el post, inténtalo nuevamente.");
        }
    }

    @FXML
    private void cancelarEdicionPost() {
        NavigationUtil.cambiarVista(txtTitulo, "/view/main.fxml");
    }

    // Métodos para vista detalle post
    @FXML
    private void votarPositivo() {
        Post post = SessionManager.getPostActual();
        if (post != null) {
            post.setVotos(post.getVotos() + 1);
            actualizarVotos(post);
        }
    }

    @FXML
    private void votarNegativo() {
        Post post = SessionManager.getPostActual();
        if (post != null) {
            if (post.getVotos() > 0) {
                post.setVotos(post.getVotos() - 1);
                actualizarVotos(post);
            }
        }
    }

    @FXML
    private void agregarComentario() {
        if (!txtNuevoComentario.getText().isBlank()) {
            Comentario nuevo = new Comentario(
                    SessionManager.getUsuarioActual().getId(),
                    txtNuevoComentario.getText()
            );
            postService.agregarComentario(
                    SessionManager.getPostActual().getId(),
                    nuevo
            );
            txtNuevoComentario.clear();
            cargarDetallePost();
        }
    }

    private void actualizarVotos(Post post) {
        postService.actualizarPost(post);
        lblVotos.setText("Votos: " + post.getVotos());
    }

    private void cargarTagsDisponibles() {
        lvTagsDisponibles.getItems().setAll("Java", "Programación", "Tecnología");
    }

    @FXML
    private void agregarTag() {
        String seleccionado = lvTagsDisponibles.getSelectionModel().getSelectedItem();
        if (seleccionado != null && !lvTagsSeleccionados.getItems().contains(seleccionado)) {
            lvTagsSeleccionados.getItems().add(seleccionado);
        }
    }

    @FXML
    private void quitarTag() {
        String seleccionado = lvTagsSeleccionados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            lvTagsSeleccionados.getItems().remove(seleccionado);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}