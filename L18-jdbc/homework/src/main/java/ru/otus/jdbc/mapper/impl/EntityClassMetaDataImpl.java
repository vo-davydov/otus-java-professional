package ru.otus.jdbc.mapper.impl;

import ru.otus.core.annotations.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String name;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> fieldsWithoutId;
    private final List<Field> fields;

    public EntityClassMetaDataImpl(Class<T> c) {
        this.name = c.getSimpleName();
        this.constructor = getConstructor(c);
        this.idField = getIdField(c);
        this.fieldsWithoutId = getFieldsWithoutId(c);
        this.fields = getAllFields(c);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        return idField;
    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }

    private Field getIdField(Class<T> c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("ID is not specified"));
    }

    private Constructor<T> getConstructor(Class<T> c) {
        try {
            return c.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Field> getFieldsWithoutId(Class<T> c) {
        return Arrays.stream(c.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class)).toList();
    }


    private List<Field> getAllFields(Class<T> c) {
        return Arrays.asList(c.getDeclaredFields());
    }
}
