package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final String tableName;
    private final String id;
    private final String fieldsWithoutId;
    private final String fieldsWithQuestion;
    private final String fieldsQuestion;


    public <T> EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.tableName = getTableName(entityClassMetaData);
        this.id = getIdField(entityClassMetaData);
        this.fieldsWithoutId = getFieldsWithoutId(entityClassMetaData);
        this.fieldsWithQuestion = getFieldsWithQuestion(entityClassMetaData);
        this.fieldsQuestion = getFieldsQuestion(entityClassMetaData);
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + tableName;
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM " + tableName + " WHERE " + id + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO " + tableName +
                " ( " + fieldsWithoutId + " ) " +
                " VALUES ( " + fieldsQuestion + " )";
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE " + tableName + " SET " + fieldsWithQuestion
                + " WHERE " + id + " = ?";
    }


    private <T> String getFieldsWithoutId(EntityClassMetaData<T> entityClassMetaData) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        return fields.stream()
                .map(field -> field.getName().toLowerCase())
                .collect(Collectors.joining(", "));
    }

    private <T> String getFieldsQuestion(EntityClassMetaData<T> entityClassMetaData) {
        StringBuilder result = new StringBuilder();
        int size = entityClassMetaData.getFieldsWithoutId().size();
        for (int i = 0; i < size; i++) {
            result.append("?");

            if (i + 1 != size) {
                result.append(", ");
            }
        }

        return result.toString();
    }

    private <T> String getFieldsWithQuestion(EntityClassMetaData<T> entityClassMetaData) {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        return fields.stream()
                .map(field -> field.getName().toLowerCase() + " = ?")
                .collect(Collectors.joining(", "));
    }

    private <T> String getTableName(EntityClassMetaData<T> getEntityClassMetaData) {
        return getEntityClassMetaData.getName().toLowerCase();
    }

    private <T> String getIdField(EntityClassMetaData<T> entityClassMetaData) {
        return entityClassMetaData.getIdField().getName();
    }
}
