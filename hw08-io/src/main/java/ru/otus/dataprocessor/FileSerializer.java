package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {

    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        //формирует результирующий json и сохраняет его в файл
        var sorted = getSorted(data);
        writeIntoFile(sorted);
    }

    private Map<String, Double> getSorted(Map<String, Double> data) {
        return new TreeMap<>(data);
    }

    private void writeIntoFile(Map<String, Double> data) {
        var gson = new Gson();
        try (Writer writer = new FileWriter(fileName)) {
            gson.toJson(data, writer);
            writer.flush();
        } catch (IOException e) {
            throw new FileProcessException(e);
        }
    }

}
