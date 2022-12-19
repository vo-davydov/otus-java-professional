package ru.otus.services;

import ru.otus.dto.UserDto;
import ru.otus.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(long id);
    Optional<User> findRandomUser();
    Optional<User> findByLogin(String login);

    List<User> findAll();

    void save(UserDto userDto);

    UserDto getUserDtoById(Long id);
}
