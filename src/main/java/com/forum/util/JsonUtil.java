package com.forum.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    /**
     * Carga datos desde un archivo JSON y los convierte a una lista de objetos.
     * @param path Ruta del archivo JSON
     * @param tipo Clase del objeto a deserializar
     * @return Lista de objetos del tipo especificado
     */
    public static <T> List<T> cargarDatos(String path, Class<T> tipo) {
        try (Reader reader = new FileReader(path)) {
            Type tipoLista = TypeToken.getParameterized(List.class, tipo).getType();
            List<T> datos = gson.fromJson(reader, tipoLista);
            return datos != null ? datos : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * Guarda una lista de objetos en un archivo JSON.
     * @param path Ruta del archivo JSON
     * @param datos Lista de objetos a serializar
     */
    public static <T> void guardarDatos(String path, List<T> datos) {
        try (Writer writer = new FileWriter(path)) {
            gson.toJson(datos, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar datos en: " + path, e);
        }
    }

    /**
     * Busca un objeto por su ID y lo elimina del archivo JSON.
     * @param path Ruta del archivo JSON
     * @param tipo Clase del objeto
     * @param id Identificador único del objeto
     */
    public static <T> void eliminarDatos(String path, Class<T> tipo, String id) {
        List<T> datos = cargarDatos(path, tipo);
        List<T> actualizados = new ArrayList<>();

        datos.forEach(item -> {
            try {
                String itemId = item.getClass().getMethod("getId").invoke(item).toString();
                if (!itemId.equals(id)) {
                    actualizados.add(item);
                }
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar elemento", e);
            }
        });

        guardarDatos(path, actualizados);
    }

    /**
     * Busca un objeto por su ID en el archivo JSON.
     * @param path Ruta del archivo JSON
     * @param tipo Clase del objeto
     * @param id Identificador único
     * @return Objeto encontrado o null
     */
    public static <T> T buscarPorId(String path, Class<T> tipo, String id) {
        return cargarDatos(path, tipo).stream()
                .filter(item -> {
                    try {
                        String itemId = item.getClass().getMethod("getId").invoke(item).toString();
                        return itemId.equals(id);
                    } catch (Exception e) {
                        throw new RuntimeException("Error al buscar por ID", e);
                    }
                })
                .findFirst()
                .orElse(null);
    }
}