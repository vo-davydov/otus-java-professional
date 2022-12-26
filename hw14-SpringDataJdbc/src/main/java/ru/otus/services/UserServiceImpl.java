package ru.otus.services;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.otus.cache.Cache;
import ru.otus.dto.UserDto;
import ru.otus.model.User;
import ru.otus.repository.SysUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final SysUserRepository userRepository;

    @Qualifier("userCache")
    private final Cache<Long, User> cache;

    public UserServiceImpl(SysUserRepository userRepository, Cache<Long, User> cache) {
        this.userRepository = userRepository;
        this.cache = cache;
    }

    @Override
    public Optional<User> findById(long id) {
        var userFromCache = cache.get(id);

        if (userFromCache != null) {
            return Optional.of(userFromCache);
        }

        var user = userRepository.findById(id);
        user.ifPresent(value -> cache.put(value.getId(), value));

        return user;
    }

    @Override
    public Optional<User> findRandomUser() {
        var user = userRepository.findAll()
                .stream()
                .findFirst()
                .orElse(getSpecialUser());

        return Optional.of(user);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    private User getSpecialUser() {
        return new User(1997L, "vincent", "dypiastoisaisth", "Ethan Hawke");
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void save(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setLogin(userDto.getLogin());
        user.setPassword(userDto.getPassword());
        userRepository.save(user);
    }

    @Override
    public UserDto getUserDtoById(Long id) {
        var user = findById(id);

        if (user.isEmpty()) {
            return null;
        }

        return user.map(u -> {
            var userDto = new UserDto();
            userDto.setLogin(u.getLogin());
            userDto.setPassword(u.getPassword());
            userDto.setName(u.getName());
            userDto.setId(u.getId());
            return userDto;
        }).orElse(null);
    }
}
