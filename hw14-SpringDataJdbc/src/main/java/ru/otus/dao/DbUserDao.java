package ru.otus.dao;

import ru.otus.crm.dto.UserDto;
import ru.otus.crm.model.User;
import ru.otus.crm.service.DbServiceUser;

import java.util.List;
import java.util.Optional;

public class DbUserDao implements UserDao {

    private final DbServiceUser dbServiceUser;

    public DbUserDao(DbServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public Optional<User> findById(long id) {
        return dbServiceUser.getUser(id);
    }

    @Override
    public Optional<User> findRandomUser() {
        var user = dbServiceUser.findAll()
                .stream()
                .findFirst()
                .orElse(getSpecialUser());

        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return dbServiceUser.findByLogin(login);
    }

    private User getSpecialUser() {
        return new User(1L, "tesla", "123456", "Итан Хоук");
    }

    @Override
    public List<User> findAll() {
        return dbServiceUser.findAll();
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        dbServiceUser.saveUser(user);
    }
}
