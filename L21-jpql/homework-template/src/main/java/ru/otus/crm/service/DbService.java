package ru.otus.crm.service;

import ru.otus.crm.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface DbService<T extends AbstractEntity> {
    T save(T entity);

    Optional<T> get(long id);

    List<T> findAll();

    List<T> findByEntityField(String name, String value);
}
