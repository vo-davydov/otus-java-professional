package ru.otus.crm.service;

import ru.otus.crm.model.User;

import java.util.List;
import java.util.Optional;

public class DbServiceUserImpl implements DbServiceUser {
    private final DbService<User> dbService;

    public DbServiceUserImpl(DbService<User> dbService) {
        this.dbService = dbService;
    }

    @Override
    public User saveUser(User user) {
        return dbService.save(user);
    }

    @Override
    public Optional<User> getUser(long id) {
        return dbService.get(id);
    }

    @Override
    public List<User> findAll() {
        return dbService.findAll();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return dbService.findByEntityField("login", login).stream().findFirst();
    }

}
