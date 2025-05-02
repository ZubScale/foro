package com.forum.service;

import com.forum.model.CalificacionUsuario;
import java.util.List;

public interface CalificacionUsuarioService {
    void crearCalificacion(CalificacionUsuario calificacion);
    void actualizarCalificacion(CalificacionUsuario calificacion);
    void eliminarCalificacion(String id);
    List<CalificacionUsuario> listarTodas();
    List<CalificacionUsuario> listarPorPost(String postId);
}