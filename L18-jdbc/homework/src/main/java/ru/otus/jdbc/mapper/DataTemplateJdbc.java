package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    Map<String, Object> values = new HashMap<>();
                    for (var field : entityClassMetaData.getAllFields()) {
                        values.put(field.getName(), rs.getObject(field.getName()));
                    }
                    return createNewInstance(values);
                }
                return null;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), rs -> {
            try {
                List<T> result = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> values = new HashMap<>();
                    for (var field : entityClassMetaData.getAllFields()) {
                        values.put(field.getName(), rs.getObject(field.getName()));
                    }
                    result.add(createNewInstance(values));
                }
                return result;
            } catch (SQLException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    getValues(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                    getValuesForUpdate(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    private T createNewInstance(Map<String, Object> values) {
        try {
            T t = entityClassMetaData.getConstructor().newInstance();
            for (var field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                field.set(t, values.get(field.getName()));
            }
            return t;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new DataTemplateException(e);
        }
    }

    private List<Object> getValues(T client) {
        List<Object> result = new LinkedList<>();
        for (var field : entityClassMetaData.getFieldsWithoutId()) {
            try {
                Field f = client.getClass().getDeclaredField(field.getName());
                f.setAccessible(true);
                result.add(f.get(client));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new DataTemplateException(e);
            }
        }

        return result;
    }


    private List<Object> getValuesForUpdate(T client) {
        List<Object> result = getValues(client);
        try {
            var idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            result.add(idField.get(client));
        } catch (IllegalAccessException e) {
            throw new DataTemplateException(e);
        }

        return result;
    }

}
