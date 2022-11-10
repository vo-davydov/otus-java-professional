package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.cache.Cache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.crm.model.Client;
import ru.otus.core.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientImpl.class);

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final Cache<Long, Client> cache;

    public DbServiceClientImpl(TransactionManager transactionManager, DataTemplate<Client> clientDataTemplate, Cache<Long, Client> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        var c = transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                clientDataTemplate.insert(session, clientCloned);
                log.info("created client: {}", clientCloned);
                return clientCloned;
            }
            clientDataTemplate.update(session, clientCloned);
            log.info("updated client: {}", clientCloned);
            cache.put(clientCloned.getId(), clientCloned);
            return clientCloned;
        });

        cache.put(c.getId(), c);
        return client;
    }

    @Override
    public Optional<Client> getClient(long id) {
        return Optional.of(cache.get(id)).or(() ->
                transactionManager.doInReadOnlyTransaction(session -> {
                    var clientOptional = clientDataTemplate.findById(session, id);
                    log.info("client: {}", clientOptional);
                    return clientOptional;
                }));
    }

    @Override
    public List<Client> findAll() {
        var clients = transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            log.info("clientList:{}", clientList);
            return clientList;
        });

        for (var client : clients) {
            cache.put(client.getId(), client);
        }

        return clients;
    }
}
