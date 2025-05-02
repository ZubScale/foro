package com.forum.repository;

import com.forum.model.Post;
import com.forum.repository.PostRepository;
import com.forum.util.JsonUtil;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostRepositoryImpl implements PostRepository {
    private static final String FILE_PATH = "data/posts.json";

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Post> findByUsuarioId(String usuarioId) {
        List<Post> allPosts = JsonUtil.cargarDatos(FILE_PATH, Post.class);
        return allPosts.stream()
                .filter(post -> usuarioId.equals(post.getUsuarioId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Post> findByTag(String tag) {
        List<Post> allPosts = JsonUtil.cargarDatos(FILE_PATH, Post.class);
        return allPosts.stream()
                .filter(post -> post.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    // Existing implementations
    @Override
    public void save(Post post) {
        List<Post> posts = JsonUtil.cargarDatos(FILE_PATH, Post.class);
        posts.add(post);
        JsonUtil.guardarDatos(FILE_PATH, posts);
    }

    @Override
    public void update(Post updatedPost) {
        List<Post> posts = JsonUtil.cargarDatos(FILE_PATH, Post.class);
        posts = posts.stream()
                .map(p -> p.getId().equals(updatedPost.getId()) ? updatedPost : p)
                .collect(Collectors.toList());
        JsonUtil.guardarDatos(FILE_PATH, posts);
    }

    @Override
    public void delete(String id) {
        List<Post> posts = JsonUtil.cargarDatos(FILE_PATH, Post.class);
        List<Post> filtered = posts.stream()
                .filter(p -> !p.getId().equals(id))
                .collect(Collectors.toList());
        JsonUtil.guardarDatos(FILE_PATH, filtered);
    }

    @Override
    public List<Post> findAll() {
        return JsonUtil.cargarDatos(FILE_PATH, Post.class);
    }

    @Override
    public Optional<Post> findById(String id) {
        return JsonUtil.cargarDatos(FILE_PATH, Post.class).stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
}