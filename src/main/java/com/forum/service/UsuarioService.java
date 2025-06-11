package com.forum.service;

import com.forum.model.Usuario;

public interface UsuarioService {
    void registrarUsuario(Usuario usuario);
    void registrar(Usuario usuario); // Alias para compatibilidad
    void actualizarUsuario(Usuario usuario);
    void actualizarPassword(String email, String nuevaPassword);
    void eliminarUsuario(String username);
    Usuario login(String username, String password);
    boolean existeUsername(String username);
    boolean existeEmail(String email);
}