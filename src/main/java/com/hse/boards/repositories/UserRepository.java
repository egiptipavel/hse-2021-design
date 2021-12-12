package com.hse.boards.repositories;

import com.hse.boards.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findUserByLogin(String login);

    Optional<User> findUserByLoginAndPassword(String login, String password);
}
