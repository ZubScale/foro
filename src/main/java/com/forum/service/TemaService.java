package com.forum.service;

import com.forum.model.Tema;
import java.util.List;

public interface TemaService {
    void crearTema(Tema tema);
    void actualizarTema(Tema tema);
    void eliminarTema(String id);
    List<Tema> listarTodosLosTemas();
    Tema obtenerTemaPorId(String id);
}