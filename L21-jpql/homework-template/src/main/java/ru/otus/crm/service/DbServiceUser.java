package ru.otus.crm.service;

import ru.otus.crm.model.User;

import java.util.List;
import java.util.Optional;

public interface DbServiceUser {

    User saveUser(User user);

    Optional<User> getUser(long id);

    List<User> findAll();

    Optional<User> findByLogin(String login);

}
