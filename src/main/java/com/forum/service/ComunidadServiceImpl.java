package com.forum.service;

import com.forum.model.Comunidad;
import com.forum.model.Usuario;
import com.forum.repository.ComunidadRepository;
import java.util.List;

public class ComunidadServiceImpl implements ComunidadService {
    private final ComunidadRepository comunidadRepo;

    public ComunidadServiceImpl(ComunidadRepository comunidadRepo) {
        this.comunidadRepo = comunidadRepo;
    }

    @Override
    public void crearComunidad(Comunidad comunidad) {
        validarComunidad(comunidad);
        comunidadRepo.save(comunidad);
    }

    @Override
    public void actualizarComunidad(Comunidad comunidad) {
        validarComunidad(comunidad);
        if (!comunidadRepo.existsById(comunidad.getId())) {
            throw new IllegalArgumentException("Comunidad no existe");
        }
        comunidadRepo.update(comunidad);
    }

    @Override
    public void eliminarComunidad(String id) {
        if (!comunidadRepo.existsById(id)) {
            throw new IllegalArgumentException("ID de comunidad inválido");
        }
        comunidadRepo.delete(id);
    }

    @Override
    public void seguirComunidad(String comunidadId, Usuario usuario) {
        Comunidad comunidad = comunidadRepo.findById(comunidadId)
                .orElseThrow(() -> new IllegalArgumentException("Comunidad no encontrada"));

        comunidad.seguirComunidad(usuario.getId());
        comunidadRepo.update(comunidad);
    }

    @Override
    public void dejarComunidad(String comunidadId, Usuario usuario) {
        Comunidad comunidad = comunidadRepo.findById(comunidadId)
                .orElseThrow(() -> new IllegalArgumentException("Comunidad no encontrada"));

        comunidad.dejarComunidad(usuario.getId());
        comunidadRepo.update(comunidad);
    }

    @Override
    public List<Comunidad> listarTodas() {
        return comunidadRepo.findAll();
    }

    @Override
    public Comunidad obtenerPorId(String id) {
        return comunidadRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Comunidad no encontrada"));
    }

    private void validarComunidad(Comunidad comunidad) {
        if (comunidad.getNombre() == null || comunidad.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre de comunidad requerido");
        }
    }
}