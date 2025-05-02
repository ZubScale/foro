package com.forum.service;

import com.forum.model.Tema;
import com.forum.repository.TemaRepository;
import java.util.List;

public class TemaServiceImpl implements TemaService {
    private final TemaRepository temaRepo;

    public TemaServiceImpl(TemaRepository temaRepo) {
        this.temaRepo = temaRepo;
    }

    @Override
    public void crearTema(Tema tema) {
        if (tema.getNombre() == null || tema.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del tema no puede estar vacío");
        }
        temaRepo.save(tema);
    }

    @Override
    public void actualizarTema(Tema tema) {
        temaRepo.update(tema);
    }

    @Override
    public void eliminarTema(String id) {
        temaRepo.delete(id);
    }

    @Override
    public List<Tema> listarTodosLosTemas() {
        return temaRepo.findAll();
    }

    @Override
    public Tema obtenerTemaPorId(String id) {
        return temaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tema no encontrado"));
    }
}