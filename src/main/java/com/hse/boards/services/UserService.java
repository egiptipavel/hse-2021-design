package com.hse.boards.services;

import com.hse.boards.models.User;
import com.hse.boards.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component("UserService")
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void createUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }
}
