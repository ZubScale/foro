package com.forum.service;

import com.forum.model.TipoCalificacion;
import com.forum.repository.TipoCalificacionRepository;
import java.util.List;

public class TipoCalificacionServiceImpl implements TipoCalificacionService {
    private final TipoCalificacionRepository tipoRepo;

    public TipoCalificacionServiceImpl(TipoCalificacionRepository tipoRepo) {
        this.tipoRepo = tipoRepo;
    }

    @Override
    public void crearTipo(TipoCalificacion tipo) {
        if (tipo.getDescripcion() == null || tipo.getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del tipo es obligatoria");
        }
        tipoRepo.save(tipo);
    }

    @Override
    public void actualizarTipo(TipoCalificacion tipo) {

    }

    @Override
    public void eliminarTipo(String id) {

    }

    @Override
    public List<TipoCalificacion> listarTodos() {
        return tipoRepo.findAll();
    }

    @Override
    public TipoCalificacion obtenerPorId(String id) {
        return tipoRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo no encontrado"));
    }

    @Override
    public TipoCalificacion obtenerPorDescripcion(String descripcion) {
        return tipoRepo.findByDescripcion(descripcion)
                .orElseThrow(() -> new IllegalArgumentException("Tipo no encontrado"));
    }
}