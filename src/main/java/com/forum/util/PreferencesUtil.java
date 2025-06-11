package com.forum.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Utilidad para gestionar las preferencias de la aplicación
 * Usa archivos JSON para almacenar configuraciones del usuario
 */
public class PreferencesUtil {
    private static final Logger logger = Logger.getLogger(PreferencesUtil.class.getName());
    private static final String PREFS_FILE = "config/preferences.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static Map<String, Object> preferences = new HashMap<>();

    // Claves para las preferencias
    private static final String KEY_THEME = "app.theme";
    private static final String KEY_LANGUAGE = "app.language";
    private static final String KEY_FONT_SIZE = "app.fontSize";
    private static final String KEY_AUTO_LOGIN = "app.autoLogin";
    private static final String KEY_REMEMBER_USERNAME = "app.rememberUsername";
    private static final String KEY_SAVED_USERNAME = "app.savedUsername";
    private static final String KEY_WINDOW_WIDTH = "window.width";
    private static final String KEY_WINDOW_HEIGHT = "window.height";
    private static final String KEY_WINDOW_X = "window.x";
    private static final String KEY_WINDOW_Y = "window.y";
    private static final String KEY_WINDOW_MAXIMIZED = "window.maximized";
    private static final String KEY_NOTIFICATIONS_ENABLED = "notifications.enabled";
    private static final String KEY_NOTIFICATION_SOUND = "notifications.sound";
    private static final String KEY_NOTIFICATION_DESKTOP = "notifications.desktop";
    private static final String KEY_POSTS_PER_PAGE = "posts.perPage";
    private static final String KEY_DEFAULT_SORT = "posts.defaultSort";
    private static final String KEY_SHOW_AVATARS = "ui.showAvatars";
    private static final String KEY_SHOW_SIGNATURES = "ui.showSignatures";
    private static final String KEY_ENABLE_ANIMATIONS = "ui.enableAnimations";
    private static final String KEY_FIRST_RUN = "app.firstRun";

    // Valores por defecto
    private static final String DEFAULT_THEME = "light";
    private static final String DEFAULT_LANGUAGE = "es";
    private static final int DEFAULT_FONT_SIZE = 14;
    private static final boolean DEFAULT_AUTO_LOGIN = false;
    private static final boolean DEFAULT_REMEMBER_USERNAME = true;
    private static final double DEFAULT_WINDOW_WIDTH = 1024;
    private static final double DEFAULT_WINDOW_HEIGHT = 768;
    private static final boolean DEFAULT_NOTIFICATIONS = true;
    private static final boolean DEFAULT_NOTIFICATION_SOUND = true;
    private static final boolean DEFAULT_NOTIFICATION_DESKTOP = false;
    private static final int DEFAULT_POSTS_PER_PAGE = 20;
    private static final String DEFAULT_SORT = "recent";
    private static final boolean DEFAULT_SHOW_AVATARS = true;
    private static final boolean DEFAULT_SHOW_SIGNATURES = true;
    private static final boolean DEFAULT_ENABLE_ANIMATIONS = true;

    // Inicialización estática
    static {
        loadPreferences();
    }

    // Temas disponibles
    public enum Theme {
        LIGHT("light", "Tema Claro"),
        DARK("dark", "Tema Oscuro"),
        AUTO("auto", "Automático");

        private final String value;
        private final String displayName;

        Theme(String value, String displayName) {
            this.value = value;
            this.displayName = displayName;
        }

        public String getValue() { return value; }
        public String getDisplayName() { return displayName; }

        public static Theme fromValue(String value) {
            for (Theme theme : values()) {
                if (theme.value.equals(value)) {
                    return theme;
                }
            }
            return LIGHT;
        }
    }

    // Ordenamiento de posts
    public enum PostSort {
        RECENT("recent", "Más recientes"),
        POPULAR("popular", "Más populares"),
        DISCUSSED("discussed", "Más comentados"),
        VOTES("votes", "Más votados");

        private final String value;
        private final String displayName;

        PostSort(String value, String displayName) {
            this.value = value;
            this.displayName = displayName;
        }

        public String getValue() { return value; }
        public String getDisplayName() { return displayName; }

        public static PostSort fromValue(String value) {
            for (PostSort sort : values()) {
                if (sort.value.equals(value)) {
                    return sort;
                }
            }
            return RECENT;
        }
    }

    // Cargar preferencias desde archivo
    private static void loadPreferences() {
        try {
            File file = new File(PREFS_FILE);
            if (file.exists()) {
                String json = new String(Files.readAllBytes(Paths.get(PREFS_FILE)));
                preferences = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
                if (preferences == null) {
                    preferences = new HashMap<>();
                }
            } else {
                // Crear archivo con valores por defecto
                preferences = new HashMap<>();
                initializeDefaults();
                savePreferences();
            }
        } catch (IOException e) {
            logger.severe("Error al cargar preferencias: " + e.getMessage());
            preferences = new HashMap<>();
            initializeDefaults();
        }
    }

    // Guardar preferencias en archivo
    private static void savePreferences() {
        try {
            // Crear directorio si no existe
            Path configDir = Paths.get("config");
            if (!Files.exists(configDir)) {
                Files.createDirectories(configDir);
            }

            // Guardar JSON
            try (FileWriter writer = new FileWriter(PREFS_FILE)) {
                gson.toJson(preferences, writer);
            }
        } catch (IOException e) {
            logger.severe("Error al guardar preferencias: " + e.getMessage());
        }
    }

    // Inicializar valores por defecto
    private static void initializeDefaults() {
        preferences.put(KEY_THEME, DEFAULT_THEME);
        preferences.put(KEY_LANGUAGE, DEFAULT_LANGUAGE);
        preferences.put(KEY_FONT_SIZE, DEFAULT_FONT_SIZE);
        preferences.put(KEY_AUTO_LOGIN, DEFAULT_AUTO_LOGIN);
        preferences.put(KEY_REMEMBER_USERNAME, DEFAULT_REMEMBER_USERNAME);
        preferences.put(KEY_WINDOW_WIDTH, DEFAULT_WINDOW_WIDTH);
        preferences.put(KEY_WINDOW_HEIGHT, DEFAULT_WINDOW_HEIGHT);
        preferences.put(KEY_NOTIFICATIONS_ENABLED, DEFAULT_NOTIFICATIONS);
        preferences.put(KEY_NOTIFICATION_SOUND, DEFAULT_NOTIFICATION_SOUND);
        preferences.put(KEY_NOTIFICATION_DESKTOP, DEFAULT_NOTIFICATION_DESKTOP);
        preferences.put(KEY_POSTS_PER_PAGE, DEFAULT_POSTS_PER_PAGE);
        preferences.put(KEY_DEFAULT_SORT, DEFAULT_SORT);
        preferences.put(KEY_SHOW_AVATARS, DEFAULT_SHOW_AVATARS);
        preferences.put(KEY_SHOW_SIGNATURES, DEFAULT_SHOW_SIGNATURES);
        preferences.put(KEY_ENABLE_ANIMATIONS, DEFAULT_ENABLE_ANIMATIONS);
        preferences.put(KEY_FIRST_RUN, true);
    }

    // Métodos auxiliares para obtener valores
    private static String getString(String key, String defaultValue) {
        Object value = preferences.get(key);
        return value != null ? value.toString() : defaultValue;
    }

    private static int getInt(String key, int defaultValue) {
        Object value = preferences.get(key);
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return value != null ? Integer.parseInt(value.toString()) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static double getDouble(String key, double defaultValue) {
        Object value = preferences.get(key);
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return value != null ? Double.parseDouble(value.toString()) : defaultValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static boolean getBoolean(String key, boolean defaultValue) {
        Object value = preferences.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return value != null ? Boolean.parseBoolean(value.toString()) : defaultValue;
    }

    // Métodos para tema
    public static Theme getTheme() {
        String themeValue = getString(KEY_THEME, DEFAULT_THEME);
        return Theme.fromValue(themeValue);
    }

    public static void setTheme(Theme theme) {
        preferences.put(KEY_THEME, theme.getValue());
        savePreferences();
        logger.info("Tema cambiado a: " + theme.getDisplayName());
    }

    // Métodos para idioma
    public static String getLanguage() {
        return getString(KEY_LANGUAGE, DEFAULT_LANGUAGE);
    }

    public static void setLanguage(String language) {
        preferences.put(KEY_LANGUAGE, language);
        savePreferences();
        logger.info("Idioma cambiado a: " + language);
    }

    // Métodos para tamaño de fuente
    public static int getFontSize() {
        return getInt(KEY_FONT_SIZE, DEFAULT_FONT_SIZE);
    }

    public static void setFontSize(int size) {
        if (size >= 10 && size <= 24) {
            preferences.put(KEY_FONT_SIZE, size);
            savePreferences();
            logger.info("Tamaño de fuente cambiado a: " + size);
        }
    }

    // Métodos para auto-login
    public static boolean isAutoLoginEnabled() {
        return getBoolean(KEY_AUTO_LOGIN, DEFAULT_AUTO_LOGIN);
    }

    public static void setAutoLogin(boolean enabled) {
        preferences.put(KEY_AUTO_LOGIN, enabled);
        savePreferences();
    }

    // Métodos para recordar usuario
    public static boolean isRememberUsernameEnabled() {
        return getBoolean(KEY_REMEMBER_USERNAME, DEFAULT_REMEMBER_USERNAME);
    }

    public static void setRememberUsername(boolean enabled) {
        preferences.put(KEY_REMEMBER_USERNAME, enabled);
        if (!enabled) {
            clearSavedUsername();
        }
        savePreferences();
    }

    public static String getSavedUsername() {
        return getString(KEY_SAVED_USERNAME, "");
    }

    public static void setSavedUsername(String username) {
        if (username != null && !username.trim().isEmpty()) {
            preferences.put(KEY_SAVED_USERNAME, username);
            savePreferences();
        }
    }

    public static void clearSavedUsername() {
        preferences.remove(KEY_SAVED_USERNAME);
        savePreferences();
    }

    // Métodos para ventana
    public static double getWindowWidth() {
        return getDouble(KEY_WINDOW_WIDTH, DEFAULT_WINDOW_WIDTH);
    }

    public static void setWindowWidth(double width) {
        preferences.put(KEY_WINDOW_WIDTH, width);
        savePreferences();
    }

    public static double getWindowHeight() {
        return getDouble(KEY_WINDOW_HEIGHT, DEFAULT_WINDOW_HEIGHT);
    }

    public static void setWindowHeight(double height) {
        preferences.put(KEY_WINDOW_HEIGHT, height);
        savePreferences();
    }

    public static double getWindowX() {
        return getDouble(KEY_WINDOW_X, 100);
    }

    public static void setWindowX(double x) {
        preferences.put(KEY_WINDOW_X, x);
        savePreferences();
    }

    public static double getWindowY() {
        return getDouble(KEY_WINDOW_Y, 100);
    }

    public static void setWindowY(double y) {
        preferences.put(KEY_WINDOW_Y, y);
        savePreferences();
    }

    public static boolean isWindowMaximized() {
        return getBoolean(KEY_WINDOW_MAXIMIZED, false);
    }

    public static void setWindowMaximized(boolean maximized) {
        preferences.put(KEY_WINDOW_MAXIMIZED, maximized);
        savePreferences();
    }

    // Métodos para notificaciones
    public static boolean areNotificationsEnabled() {
        return getBoolean(KEY_NOTIFICATIONS_ENABLED, DEFAULT_NOTIFICATIONS);
    }

    public static void setNotificationsEnabled(boolean enabled) {
        preferences.put(KEY_NOTIFICATIONS_ENABLED, enabled);
        savePreferences();
    }

    public static boolean isNotificationSoundEnabled() {
        return getBoolean(KEY_NOTIFICATION_SOUND, DEFAULT_NOTIFICATION_SOUND);
    }

    public static void setNotificationSound(boolean enabled) {
        preferences.put(KEY_NOTIFICATION_SOUND, enabled);
        savePreferences();
    }

    public static boolean isDesktopNotificationEnabled() {
        return getBoolean(KEY_NOTIFICATION_DESKTOP, DEFAULT_NOTIFICATION_DESKTOP);
    }

    public static void setDesktopNotification(boolean enabled) {
        preferences.put(KEY_NOTIFICATION_DESKTOP, enabled);
        savePreferences();
    }

    // Métodos para posts
    public static int getPostsPerPage() {
        return getInt(KEY_POSTS_PER_PAGE, DEFAULT_POSTS_PER_PAGE);
    }

    public static void setPostsPerPage(int count) {
        if (count >= 10 && count <= 100) {
            preferences.put(KEY_POSTS_PER_PAGE, count);
            savePreferences();
        }
    }

    public static PostSort getDefaultPostSort() {
        String sortValue = getString(KEY_DEFAULT_SORT, DEFAULT_SORT);
        return PostSort.fromValue(sortValue);
    }

    public static void setDefaultPostSort(PostSort sort) {
        preferences.put(KEY_DEFAULT_SORT, sort.getValue());
        savePreferences();
    }

    // Métodos para UI
    public static boolean showAvatars() {
        return getBoolean(KEY_SHOW_AVATARS, DEFAULT_SHOW_AVATARS);
    }

    public static void setShowAvatars(boolean show) {
        preferences.put(KEY_SHOW_AVATARS, show);
        savePreferences();
    }

    public static boolean showSignatures() {
        return getBoolean(KEY_SHOW_SIGNATURES, DEFAULT_SHOW_SIGNATURES);
    }

    public static void setShowSignatures(boolean show) {
        preferences.put(KEY_SHOW_SIGNATURES, show);
        savePreferences();
    }

    public static boolean areAnimationsEnabled() {
        return getBoolean(KEY_ENABLE_ANIMATIONS, DEFAULT_ENABLE_ANIMATIONS);
    }

    public static void setAnimationsEnabled(boolean enabled) {
        preferences.put(KEY_ENABLE_ANIMATIONS, enabled);
        savePreferences();
    }

    // Método para resetear todas las preferencias
    public static void resetToDefaults() {
        preferences.clear();
        initializeDefaults();
        savePreferences();
        logger.info("Todas las preferencias han sido restablecidas a sus valores por defecto");
    }

    // Método para exportar preferencias (útil para backup)
    public static String exportPreferences() {
        return gson.toJson(preferences);
    }

    // Método para importar preferencias
    public static void importPreferences(String json) {
        try {
            Map<String, Object> imported = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());
            if (imported != null) {
                preferences.clear();
                preferences.putAll(imported);
                savePreferences();
                logger.info("Preferencias importadas exitosamente");
            }
        } catch (Exception e) {
            logger.severe("Error al importar preferencias: " + e.getMessage());
        }
    }

    // Método para verificar si es la primera vez que se ejecuta la app
    public static boolean isFirstRun() {
        boolean firstRun = getBoolean(KEY_FIRST_RUN, true);
        if (firstRun) {
            preferences.put(KEY_FIRST_RUN, false);
            savePreferences();
        }
        return firstRun;
    }

    // Método para recargar preferencias desde archivo
    public static void reload() {
        loadPreferences();
        logger.info("Preferencias recargadas desde archivo");
    }

    // Constructor privado para evitar instanciación
    private PreferencesUtil() {
        throw new AssertionError("PreferencesUtil no debe ser instanciada");
    }
}