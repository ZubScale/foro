package com.forum.service;

import com.forum.model.Comentario;
import com.forum.model.Post;
import java.util.List;

public interface PostService {
    void crearPost(Post post);
    void actualizarPost(Post post);
    void eliminarPost(String postId);
    Post obtenerPostPorId(String postId); // Nombre corregido
    List<Post> obtenerPostsPorUsuario(String usuarioId);
    List<Post> buscarPostsPorTag(String tag);
    void agregarComentario(String postId, Comentario comentario);
    List<Comentario> obtenerComentarios(String postId);
}