package com.forum.util;

import com.forum.model.Comunidad;
import com.forum.model.Post;
import com.forum.model.Usuario;

import java.util.prefs.Preferences;

public class SessionManager {
    private static Usuario usuarioActual;
    private static Post postActual;
    private static Comunidad comunidadActual;
    private static final Preferences prefs = Preferences.userNodeForPackage(SessionManager.class);
    private static final String USUARIO_KEY = "savedUsername";

    public static void login(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static void logout() {
        usuarioActual = null;
        postActual = null;
        comunidadActual = null;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Post getPostActual() {
        return postActual;
    }

    public static void setPostActual(Post post) {
        postActual = post;
    }

    public static Comunidad getComunidadActual() {
        return comunidadActual;
    }

    public static void setComunidadActual(Comunidad comunidad) {
        comunidadActual = comunidad;
    }

    public static boolean isLoggedIn() {
        return usuarioActual != null;
    }

    public static void guardarCredenciales(String username) {
        prefs.put(USUARIO_KEY, username);
    }

    public static String getCredencialesGuardadas() {
        return prefs.get(USUARIO_KEY, null);
    }

    public static void limpiarCredenciales() {
        prefs.remove(USUARIO_KEY);
    }

    public static boolean esAdmin() {
        return usuarioActual != null && usuarioActual.isAdmin();
    }

    public static boolean esModerador(String comunidadId) {
        if (usuarioActual == null || comunidadActual == null) {
            return false;
        }
        return comunidadActual.esModerador(usuarioActual.getId());
    }
}