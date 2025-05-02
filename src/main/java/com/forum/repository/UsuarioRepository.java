package com.forum.repository;

import com.forum.model.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    void save(Usuario usuario);

    void update(Usuario usuario);

    void delete(String username);

    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    boolean existePorEmail(String email);
}