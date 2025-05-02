package com.forum.service;

import com.forum.model.Post;
import com.forum.repository.PostRepository;
import com.forum.model.Comentario;

import java.util.List;
import java.util.UUID;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;

    public PostServiceImpl(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    @Override
    public void crearPost(Post post) {
        validarPost(post);
        post.setId(generarIdUnico());
        postRepo.save(post);
    }

    @Override
    public void actualizarPost(Post post) {
        if (!postRepo.existsById(post.getId())) {
            throw new IllegalStateException("Post no encontrado");
        }
        validarPost(post);
        postRepo.update(post);
    }

    @Override
    public void eliminarPost(String postId) {
        if (!postRepo.existsById(postId)) {
            throw new IllegalStateException("Post no encontrado");
        }
        postRepo.delete(postId);
    }

    @Override
    public Post obtenerPostPorId(String postId) {
        return postRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("ID de post inválido"));
    }

    @Override
    public List<Post> obtenerPostsPorUsuario(String usuarioId) {
        if (usuarioId == null || usuarioId.isBlank()) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        return postRepo.findByUsuarioId(usuarioId);
    }

    @Override
    public List<Post> buscarPostsPorTag(String tag) {
        if (tag == null || tag.isBlank()) {
            throw new IllegalArgumentException("Tag no puede estar vacío");
        }
        return postRepo.findByTag(tag);
    }

    @Override
    public void agregarComentario(String postId, Comentario comentario) {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post no encontrado"));

        post.getComentarios().add(comentario);
        postRepo.update(post);
    }

    @Override
    public List<Comentario> obtenerComentarios(String postId) {
        return postRepo.findById(postId)
                .map(Post::getComentarios)
                .orElseThrow(() -> new IllegalArgumentException("Post no encontrado"));
    }

    private void validarPost(Post post) {
        if (post.getContenido() == null || post.getContenido().isBlank()) {
            throw new IllegalArgumentException("Contenido del post requerido");
        }
        if (post.getUsuarioId() == null || post.getUsuarioId().isBlank()) {
            throw new IllegalArgumentException("Usuario no válido");
        }
    }

    private String generarIdUnico() {
        return UUID.randomUUID().toString();
    }
}