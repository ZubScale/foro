package com.forum.repository;

import com.forum.model.Comunidad;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.google.gson.*;

public class ComunidadRepositoryImpl implements ComunidadRepository {
    private static final String FILE_PATH = "src/main/resources/data/comunidades.json";
    private final Gson gson;

    public ComunidadRepositoryImpl() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
                    }
                })
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                    }
                })
                .setPrettyPrinting()
                .create();
    }

    @Override
    public void save(Comunidad comunidad) {
        List<Comunidad> comunidades = findAll();
        comunidades.add(comunidad);
        writeToFile(comunidades);
    }

    @Override
    public void update(Comunidad comunidad) {
        List<Comunidad> comunidades = findAll();
        comunidades.replaceAll(c -> c.getId().equals(comunidad.getId()) ? comunidad : c);
        writeToFile(comunidades);
    }

    @Override
    public void delete(String id) {
        List<Comunidad> comunidades = findAll();
        comunidades.removeIf(c -> c.getId().equals(id));
        writeToFile(comunidades);
    }

    @Override
    public List<Comunidad> findAll() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                writeToFile(new ArrayList<>());
                return new ArrayList<>();
            }

            try (Reader reader = new FileReader(FILE_PATH)) {
                List<Comunidad> comunidades = gson.fromJson(reader, new TypeToken<ArrayList<Comunidad>>(){}.getType());
                return comunidades != null ? comunidades : new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Comunidad> findById(String id) {
        return findAll().stream()
                .filter(c -> c.getId() != null && c.getId().equals(id))
                .findFirst();
    }

    @Override
    public boolean existsById(String id) {
        return findById(id).isPresent();
    }

    private void writeToFile(List<Comunidad> comunidades) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();

            try (Writer writer = new FileWriter(FILE_PATH)) {
                gson.toJson(comunidades, writer);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar comunidades", e);
        }
    }
}