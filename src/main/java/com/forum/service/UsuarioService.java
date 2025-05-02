package com.forum.service;

import com.forum.model.Usuario;

public interface UsuarioService {
    void registrarUsuario(Usuario usuario);
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuario(String username);
    Usuario login(String username, String password);
    boolean existeUsername(String username);
    boolean existeEmail(String email);

}