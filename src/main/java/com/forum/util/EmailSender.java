package com.forum.util;

import java.util.logging.Logger;

public class EmailSender {
    private static final Logger logger = Logger.getLogger(EmailSender.class.getName());


    public static void enviarCodigoVerificacion(String email, String codigo) {
        // Simulación de envío de email
        logger.info("=== SIMULACIÓN DE EMAIL ===");
        logger.info("Para: " + email);
        logger.info("Asunto: Código de verificación - ForoApp");
        logger.info("Mensaje: Tu código de verificación es: " + codigo);
        logger.info("Este código expirará en 5 minutos.");
        logger.info("=========================");

        // En una aplicación real, aquí iría la integración con JavaMail o similar


        // Para efectos de desarrollo, mostramos el código en consola
        System.out.println("\n*** CÓDIGO DE VERIFICACIÓN PARA " + email + ": " + codigo + " ***\n");
    }

    /**
     * Simula el envío de un email de bienvenida
     */
    public static void enviarBienvenida(String email, String nombreUsuario) {
        logger.info("=== EMAIL DE BIENVENIDA ===");
        logger.info("Para: " + email);
        logger.info("Asunto: ¡Bienvenido a ForoApp!");
        logger.info("Mensaje: Hola " + nombreUsuario + ", gracias por registrarte en ForoApp.");
        logger.info("==========================");
    }

    /**
     * Simula el envío de notificación de nuevo mensaje
     */
    public static void enviarNotificacionMensaje(String email, String remitente) {
        logger.info("=== NOTIFICACIÓN DE MENSAJE ===");
        logger.info("Para: " + email);
        logger.info("Asunto: Nuevo mensaje de " + remitente);
        logger.info("Mensaje: Has recibido un nuevo mensaje en ForoApp.");
        logger.info("==============================");
    }
}