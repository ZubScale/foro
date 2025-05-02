package com.forum.service;

import com.forum.model.Comunidad;
import com.forum.model.Usuario;
import java.util.List;

public interface ComunidadService {
    void crearComunidad(Comunidad comunidad);
    void actualizarComunidad(Comunidad comunidad);
    void eliminarComunidad(String id);
    void seguirComunidad(String comunidadId, Usuario usuario);
    void dejarComunidad(String comunidadId, Usuario usuario);
    List<Comunidad> listarTodas();
    Comunidad obtenerPorId(String id);
}