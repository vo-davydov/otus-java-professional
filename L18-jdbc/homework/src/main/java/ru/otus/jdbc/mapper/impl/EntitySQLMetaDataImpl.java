package ru.otus.jdbc.mapper.impl;

import ru.otus.jdbc.mapper.EntityClassMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaData;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {

    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + getTableName();
    }

    @Override
    public String getSelectByIdSql() {
        return "SELECT * FROM " + getTableName() + " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO " + getTableName() +
                " ( " + getFieldsWithoutId() + " ) " +
                " VALUES ( " + getFieldsQuestion() + " )";
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE " + getTableName() + " SET " + getFieldsWithQuestion()
                + " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }


    private String getFieldsWithoutId() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        return fields.stream()
                .map(field -> field.getName().toLowerCase())
                .collect(Collectors.joining(", "));
    }

    private String getFieldsQuestion() {
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

    private String getFieldsWithQuestion() {
        List<Field> fields = entityClassMetaData.getFieldsWithoutId();
        return fields.stream()
                .map(field -> field.getName().toLowerCase() + " = ?")
                .collect(Collectors.joining(", "));
    }

    private String getTableName() {
        return entityClassMetaData.getName().toLowerCase();
    }

    public EntityClassMetaData getEntityClassMetaData() {
        return entityClassMetaData;
    }

}
