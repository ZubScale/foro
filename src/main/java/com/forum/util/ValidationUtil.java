package com.forum.util;

import java.util.regex.Pattern;
import java.time.LocalDate;
import java.time.Period;

public class ValidationUtil {
    // Patrones de expresiones regulares
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern USERNAME_PATTERN = 
        Pattern.compile("^[a-zA-Z0-9_-]{3,20}$");
    
    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    
    private static final Pattern URL_PATTERN = 
        Pattern.compile("^(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?$");
    
    private static final Pattern NOMBRE_PATTERN = 
        Pattern.compile("^[\\p{L}\\s]{2,50}$");

    // Validaciones de texto
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isValidNombre(String nombre) {
        return nombre != null && NOMBRE_PATTERN.matcher(nombre).matches();
    }

    public static boolean isValidUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    // Validaciones de longitud
    public static boolean isValidLength(String texto, int minLength, int maxLength) {
        return texto != null && texto.length() >= minLength && texto.length() <= maxLength;
    }

    // Validaciones de edad
    public static boolean isValidAge(LocalDate fechaNacimiento, int edadMinima) {
        if (fechaNacimiento == null) return false;
        Period periodo = Period.between(fechaNacimiento, LocalDate.now());
        return periodo.getYears() >= edadMinima;
    }

    // Validaciones numéricas
    public static boolean isValidRange(int valor, int min, int max) {
        return valor >= min && valor <= max;
    }

    public static boolean isValidRange(double valor, double min, double max) {
        return valor >= min && valor <= max;
    }

    // Validaciones específicas de contenido
    public static String sanitizeText(String texto) {
        if (texto == null) return "";
        return texto.trim().replaceAll("[<>]", "");
    }

    public static boolean containsOnlyNumbers(String texto) {
        return texto != null && texto.matches("\\d+");
    }

    public static boolean containsOnlyLetters(String texto) {
        return texto != null && texto.matches("[\\p{L}\\s]+");
    }

    public static boolean isValidPhoneNumber(String telefono) {
        return telefono != null && telefono.matches("\\+?\\d{8,15}");
    }

    // Métodos de validación con mensajes de error
    public static ValidationResult validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return new ValidationResult(false, "El email es requerido");
        }
        if (!isValidEmail(email)) {
            return new ValidationResult(false, "El formato del email no es válido");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return new ValidationResult(false, "La contraseña es requerida");
        }
        if (!isValidPassword(password)) {
            return new ValidationResult(false, 
                "La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, " +
                "minúsculas, números y caracteres especiales");
        }
        return new ValidationResult(true, "");
    }

    public static ValidationResult validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return new ValidationResult(false, "El nombre de usuario es requerido");
        }
        if (!isValidUsername(username)) {
            return new ValidationResult(false, 
                "El nombre de usuario debe tener entre 3 y 20 caracteres y solo puede " +
                "contener letras, números, guiones y guiones bajos");
        }
        return new ValidationResult(true, "");
    }

    // Clase interna para resultados de validación
    public static class ValidationResult {
        private final boolean valid;
        private final String message;

        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }
    }

    // Constructor privado para evitar instanciación
    private void ValidationUtils() {
        throw new AssertionError("La clase ValidationUtil no debe ser instanciada");
    }
}