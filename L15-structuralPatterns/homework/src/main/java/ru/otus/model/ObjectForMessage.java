package ru.otus.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ObjectForMessage {
    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if (data != null) {
            return "ObjectForMessage{" +
                    "data= " + data.stream()
                    .map(Objects::toString)
                    .collect(Collectors.joining(", ")) +
                    '}';
        }

        return "ObjectForMessage { }";
    }

    public ObjectForMessage clone() {
        ObjectForMessage object = new ObjectForMessage();
        object.setData(new ArrayList<>(this.data));
        return object;
    }
}
