// CalificacionUsuarioServiceImpl.java
package com.forum.service;

import com.forum.model.CalificacionUsuario;
import com.forum.repository.CalificacionUsuarioRepository;
import java.util.List;

public class CalificacionUsuarioServiceImpl implements CalificacionUsuarioService {
    private final CalificacionUsuarioRepository calificacionRepo;

    public CalificacionUsuarioServiceImpl(CalificacionUsuarioRepository calificacionRepo) {
        this.calificacionRepo = calificacionRepo;
    }

    @Override
    public void crearCalificacion(CalificacionUsuario calificacion) {
        validarCalificacion(calificacion);
        calificacionRepo.save(calificacion);
    }

    @Override
    public void actualizarCalificacion(CalificacionUsuario calificacion) {
        validarCalificacion(calificacion);
        calificacionRepo.update(calificacion);
    }

    @Override
    public void eliminarCalificacion(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID inválido");
        }
        calificacionRepo.delete(id);
    }

    @Override
    public List<CalificacionUsuario> listarTodas() {
        return calificacionRepo.findAll();
    }

    @Override
    public List<CalificacionUsuario> listarPorPost(String postId) {
        return calificacionRepo.findByPostId(postId); //
    }

    private void validarCalificacion(CalificacionUsuario calificacion) {
        if (calificacion.getPostId() == null || calificacion.getUsuarioId() == null) {
            throw new IllegalArgumentException("Post y Usuario son obligatorios");
        }
    }
}