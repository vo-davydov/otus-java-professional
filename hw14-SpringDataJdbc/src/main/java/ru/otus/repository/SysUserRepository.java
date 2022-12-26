package ru.otus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface SysUserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(String login);

    List<User> findAll();

}
