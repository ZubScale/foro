package com.forum.repository;

import com.forum.model.CalificacionUsuario;
import com.forum.repository.CalificacionUsuarioRepository;
import com.forum.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CalificacionUsuarioRepositoryImpl implements CalificacionUsuarioRepository {

    private static final String FILE_PATH = "data/calificaciones_usuario.json";

    @Override
    public void save(CalificacionUsuario calificacion) {
        List<CalificacionUsuario> calificaciones = findAll();
        calificaciones.add(calificacion);
        JsonUtil.guardarDatos(FILE_PATH, calificaciones);
    }

    @Override
    public void update(CalificacionUsuario calificacion) {
        List<CalificacionUsuario> calificaciones = findAll();
        List<CalificacionUsuario> actualizadas = calificaciones.stream()
                .map(c -> c.getId().equals(calificacion.getId()) ? calificacion : c)
                .collect(Collectors.toList());
        JsonUtil.guardarDatos(FILE_PATH, actualizadas);
    }

    @Override
    public void delete(String id) {
        List<CalificacionUsuario> calificaciones = findAll();
        List<CalificacionUsuario> actualizadas = calificaciones.stream()
                .filter(c -> !c.getId().equals(id))
                .collect(Collectors.toList());
        JsonUtil.guardarDatos(FILE_PATH, actualizadas);
    }

    @Override
    public List<CalificacionUsuario> findAll() {
        List<CalificacionUsuario> calificaciones = JsonUtil.cargarDatos(FILE_PATH, CalificacionUsuario.class);
        return calificaciones != null ? calificaciones : new ArrayList<>();
    }

    @Override
    public Optional<CalificacionUsuario> findById(String id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<CalificacionUsuario> findByPostId(String postId) {
        return findAll().stream()
                .filter(c -> c.getPostId().equals(postId))  // Usa getPostId() que sí existe
                .collect(Collectors.toList());
    }
}