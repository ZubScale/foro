package com.forum.repository;

import com.forum.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostRepository {
    void save(Post post);
    void update(Post post);
    void delete(String id);
    List<Post> findAll();
    Optional<Post> findById(String id);

    // New methods added
    boolean existsById(String id);
    List<Post> findByUsuarioId(String usuarioId);
    List<Post> findByTag(String tag);
}