package com.forum.service;

import com.forum.model.Usuario;
import com.forum.repository.UsuarioRepository;
import com.forum.repository.UsuarioRepositoryImpl;
import com.forum.service.RecuperacionPasswordService;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RecuperacionPasswordServiceImpl implements RecuperacionPasswordService {

    private final UsuarioRepository usuarioRepo = new UsuarioRepositoryImpl();
    private final Map<String, String> codigosRecuperacion = new HashMap<>();
    private static final int LONGITUD_CODIGO = 6;

    @Override
    public boolean validarEmail(String email) {
        return email.matches("^[\\w.-]+@([\\w-]+\\.)+[\\w-]{2,4}$") &&
                usuarioRepo.existePorEmail(email);
    }

    @Override
    public void enviarCodigoRecuperacion(String email) {
        String codigo = generarCodigo();
        codigosRecuperacion.put(email, codigo);
        // Simular envío de código por email
        System.out.println("Código enviado a " + email + ": " + codigo);
    }

    @Override
    public void restablecerPassword(String codigo, String nuevaPassword) {
        codigosRecuperacion.entrySet().stream()
                .filter(entry -> entry.getValue().equals(codigo))
                .findFirst()
                .ifPresent(entry -> {
                    Usuario usuario = usuarioRepo.findByEmail(entry.getKey())
                            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
                    usuario.setPassword(nuevaPassword);
                    usuarioRepo.update(usuario);
                    codigosRecuperacion.remove(entry.getKey());
                });
    }

    private String generarCodigo() {
        return new Random().ints(LONGITUD_CODIGO, 0, 10)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}