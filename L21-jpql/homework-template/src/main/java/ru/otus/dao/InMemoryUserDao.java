package ru.otus.dao;

import ru.otus.crm.dto.UserDto;
import ru.otus.crm.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class InMemoryUserDao implements UserDao {

    private final Map<Long, User> users;

    public InMemoryUserDao() {
        users = new HashMap<>();
        users.put(1L, new User(1L, "Крис Гир", "user1", "11111"));
        users.put(2L, new User(2L, "Ая Кэш", "user2", "11111"));
        users.put(3L, new User(3L, "Десмин Боргес", "user3", "11111"));
        users.put(4L, new User(4L, "Кетер Донохью", "user4", "11111"));
        users.put(5L, new User(5L, "Стивен Шнайдер", "user5", "11111"));
        users.put(6L, new User(6L, "Джанет Вэрни", "user6", "11111"));
        users.put(7L, new User(7L, "Брэндон Смит", "user7", "11111"));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findRandomUser() {
        Random r = new Random();
        return users.values().stream().skip(r.nextInt(users.size() - 1)).findFirst();
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return users.values().stream().filter(v -> v.getLogin().equals(login)).findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void save(UserDto userDto) {
        var maxValue = users.keySet()
                .stream()
                .max(Long::compareTo)
                .stream()
                .findFirst()
                .orElse(Long.MAX_VALUE);

        users.put(maxValue, new User(maxValue, userDto.getLogin(), userDto.getPassword(), userDto.getName()));
    }
}
