package com.forum.service;

public interface RecuperacionPasswordService {
    boolean validarEmail(String email);
    void enviarCodigoRecuperacion(String email);
    void restablecerPassword(String codigo, String nuevaPassword);
}