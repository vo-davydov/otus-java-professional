package ru.otus.crm.service;

import ru.otus.crm.model.Client;

import java.util.List;
import java.util.Optional;

public class DbServiceClientImpl implements DBServiceClient {

    private final DbService<Client> dbService;

    public DbServiceClientImpl(DbService<Client> dbService) {
        this.dbService = dbService;
    }

    @Override
    public Client saveClient(Client client) {
        return dbService.save(client);
    }

    @Override
    public Optional<Client> getClient(long id) {
        return dbService.get(id);
    }

    @Override
    public List<Client> findAll() {
        return dbService.findAll();
    }
}
