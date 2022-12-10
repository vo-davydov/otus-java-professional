package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.AbstractEntity;

import java.util.List;
import java.util.Optional;

public class DbServiceImpl <T extends AbstractEntity<T>> implements DbService {

    private static final Logger log = LoggerFactory.getLogger(DbServiceImpl.class);
    private final DataTemplate<T> dataTemplate;
    private final TransactionManager transactionManager;

    public DbServiceImpl(TransactionManager transactionManager, DataTemplate<T> dataTemplate) {
        this.transactionManager = transactionManager;
        this.dataTemplate = dataTemplate;
    }

    @Override
    public AbstractEntity<T> save(AbstractEntity entity) {
        return  transactionManager.doInTransaction(session -> {
            var clone = entity.clone();
            if (clone.getId() == null) {
                dataTemplate.insert(session, (T) clone);
                log.info("created entity: {}", clone);
                return clone;
            }
            dataTemplate.update(session, (T) clone);
            log.info("updated entity: {}", clone);
            return clone;
        });
    }

    @Override
    public Optional<T> get(long id) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var entityOptional = dataTemplate.findById(session, id);
            log.info("entity: {}", entityOptional);
            return entityOptional;
        });
    }

    @Override
    public List<T> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var list = dataTemplate.findAll(session);
            log.info("entities:{}", list);
            return list;
        });
    }

    @Override
    public List<T> findByEntityField(String name, String value) {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var list = dataTemplate.findByEntityField(session, name, value);
            log.info("entities:{}", list);
            return list;
        });
    }
}
