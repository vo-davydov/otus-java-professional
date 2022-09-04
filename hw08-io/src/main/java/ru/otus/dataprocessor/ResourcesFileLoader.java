package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.NoSuchElementException;

public class ResourcesFileLoader implements Loader {

    private final String fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public List<Measurement> load() {
        //читает файл, парсит и возвращает результат
        var path = getPath();
        var json = getJson(path);
        return parseJson(json);
    }

    private Path getPath() {
        var fileUrl = ClassLoader.getSystemResource(fileName);
        if (fileUrl == null) {
            throw new NoSuchElementException("Resource file " + fileName + " not found");
        }

        try {
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new FileProcessException(e);
        }
    }

    private String getJson(Path path) {
        try {
            return Files.readString(path);
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }

    private List<Measurement> parseJson(String json) {
        var gson = new Gson();
        var measurementsType = new TypeToken<List<Measurement>>() {
        }.getType();
        return gson.fromJson(json, measurementsType);
    }

}
