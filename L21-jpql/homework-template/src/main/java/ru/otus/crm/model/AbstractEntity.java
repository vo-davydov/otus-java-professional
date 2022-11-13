package ru.otus.crm.model;

public interface AbstractEntity<T extends AbstractEntity<T>> {

    Long getId();

    T clone();
}
