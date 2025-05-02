package com.forum.util;

import com.forum.model.Post;
import com.forum.model.Usuario;

public class SessionManager {
    private static Usuario usuarioActual;

    public static void login(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static void logout() {
        usuarioActual = null;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static Post getPostActual() {
        return null;
    }
}