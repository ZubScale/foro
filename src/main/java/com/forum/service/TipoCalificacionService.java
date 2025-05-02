package com.forum.service;

import com.forum.model.TipoCalificacion;
import java.util.List;

public interface TipoCalificacionService {
    void crearTipo(TipoCalificacion tipo);
    void actualizarTipo(TipoCalificacion tipo);
    void eliminarTipo(String id);
    List<TipoCalificacion> listarTodos();
    TipoCalificacion obtenerPorId(String id);
    TipoCalificacion obtenerPorDescripcion(String descripcion);
}