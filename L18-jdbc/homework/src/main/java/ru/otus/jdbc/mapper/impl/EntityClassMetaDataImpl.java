package ru.otus.jdbc.mapper.impl;

import ru.otus.core.annotations.Id;
import ru.otus.jdbc.mapper.EntityClassMetaData;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final Class<T> tClass;

    public EntityClassMetaDataImpl(Class<T> c) {
        this.tClass = c;
    }

    @Override
    public String getName() {
        return tClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return tClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(()-> new RuntimeException("ID is not specified"));
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(tClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return Arrays.stream(tClass.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .toList();
    }
}
