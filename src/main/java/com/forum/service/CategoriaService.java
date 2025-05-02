package com.forum.service;

import com.forum.model.Categoria;
import java.util.List;

public interface CategoriaService {
    void crearCategoria(Categoria categoria);
    void actualizarCategoria(Categoria categoria);
    void eliminarCategoria(String id);
    List<Categoria> listarTodas();
    Categoria obtenerPorId(String id);
    Categoria obtenerPorNombre(String nombre);
}