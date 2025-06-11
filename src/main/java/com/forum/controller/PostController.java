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
import java.util.List;
import java.util.Optional;

public class PostController {

    // Elementos para edición de post
    @FXML private TextField txtTitulo;
    @FXML private TextArea txtContenido;
    @FXML private ListView<String> lvTagsDisponibles;
    @FXML private ListView<String> lvTagsSeleccionados;

    // Elementos para detalle de post
    @FXML private Text txtTituloPost;
    @FXML private Label lblAutor;
    @FXML private Label lblFecha;
    @FXML private Label lblVotos;
    @FXML private TextArea txtContenidoPost;
    @FXML private ListView<Comentario> lstComentarios;
    @FXML private TextArea txtNuevoComentario;

    private final PostService postService = new PostServiceImpl(new PostRepositoryImpl());

    @FXML
    private void initialize() {
        cargarTagsDisponibles();

        // Solo cargar detalles si está en vista de detalle
        if (txtTituloPost != null) {
            cargarDetallePost();
        }
    }

    private void cargarDetallePost() {
        Optional<Post> optionalPost = Optional.ofNullable(SessionManager.getPostActual());
        if (optionalPost.isEmpty()) return;

        Post post = optionalPost.get();
        txtTituloPost.setText(post.getTitulo());
        lblAutor.setText("Autor: " + post.getUsuarioId());
        lblFecha.setText("Fecha: " + post.getFecha());
        lblVotos.setText("Votos: " + post.getVotos());
        txtContenidoPost.setText(post.getContenido());

        List<Comentario> comentarios = Optional.ofNullable(post.getComentarios()).orElseGet(ArrayList::new);
        lstComentarios.getItems().setAll(comentarios);
    }

    @FXML
    private void guardarPostEditado() {
        if (txtTitulo.getText().isBlank() || txtContenido.getText().isBlank()) {
            mostrarAlerta("Error", "El título y contenido no pueden estar vacíos.");
            return;
        }

        Post post = SessionManager.getPostActual();
        if (post == null) {
            mostrarAlerta("Error", "No se encontró el post a editar.");
            return;
        }

        post.setTitulo(txtTitulo.getText());
        post.setContenido(txtContenido.getText());
        post.setTags(new ArrayList<>(lvTagsSeleccionados.getItems()));

        postService.actualizarPost(post);
        NavigationUtil.cambiarVista(txtTitulo, "/view/main.fxml");
    }

    @FXML
    private void cancelarEdicionPost() {
        NavigationUtil.cambiarVista(txtTitulo, "/view/main.fxml");
    }

    @FXML
    private void votarPositivo() {
        modificarVotos(1);
    }

    @FXML
    private void votarNegativo() {
        modificarVotos(-1);
    }

    private void modificarVotos(int valor) {
        Post post = SessionManager.getPostActual();
        if (post == null) return;

        int nuevosVotos = Math.max(0, post.getVotos() + valor);
        post.setVotos(nuevosVotos);
        postService.actualizarPost(post);
        lblVotos.setText("Votos: " + nuevosVotos);
    }

    @FXML
    private void agregarComentario() {
        String textoComentario = txtNuevoComentario.getText();
        if (textoComentario.isBlank()) return;

        Post post = SessionManager.getPostActual();
        if (post == null || SessionManager.getUsuarioActual() == null) return;

        Comentario comentario = new Comentario(
                SessionManager.getUsuarioActual().getId(),
                textoComentario
        );

        postService.agregarComentario(post.getId(), comentario);
        txtNuevoComentario.clear();
        cargarDetallePost(); // Recarga comentarios
    }

    private void cargarTagsDisponibles() {
        lvTagsDisponibles.getItems().setAll("Java", "Programación", "Tecnología");
    }

    @FXML
    private void agregarTag() {
        String tag = lvTagsDisponibles.getSelectionModel().getSelectedItem();
        if (tag != null && !lvTagsSeleccionados.getItems().contains(tag)) {
            lvTagsSeleccionados.getItems().add(tag);
        }
    }

    @FXML
    private void quitarTag() {
        String tag = lvTagsSeleccionados.getSelectionModel().getSelectedItem();
        if (tag != null) {
            lvTagsSeleccionados.getItems().remove(tag);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
