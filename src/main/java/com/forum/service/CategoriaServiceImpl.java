package com.forum.service;

import com.forum.model.Categoria;
import com.forum.repository.CategoriaRepository;
import java.util.List;

public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepo;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepo) {
        this.categoriaRepo = categoriaRepo;
    }

    @Override
    public void crearCategoria(Categoria categoria) {
        validarCategoria(categoria);
        categoriaRepo.save(categoria);
    }

    @Override
    public void actualizarCategoria(Categoria categoria) { // ✅ Método implementado
        validarCategoria(categoria);
        if (!categoriaRepo.existsById(categoria.getId())) {
            throw new IllegalArgumentException("Categoría no existe");
        }
        categoriaRepo.update(categoria);
    }

    @Override
    public void eliminarCategoria(String id) { // ✅ Método implementado
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID inválido");
        }
        categoriaRepo.delete(id);
    }

    @Override
    public List<Categoria> listarTodas() {
        return categoriaRepo.findAll();
    }

    @Override
    public Categoria obtenerPorId(String id) {
        return categoriaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }

    @Override
    public Categoria obtenerPorNombre(String nombre) {
        return categoriaRepo.findByNombre(nombre)
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));
    }

    private void validarCategoria(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre de categoría es requerido");
        }
    }
}