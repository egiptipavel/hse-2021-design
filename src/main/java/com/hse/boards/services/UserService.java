package com.hse.boards.services;

import com.hse.boards.models.User;
import com.hse.boards.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public User createUser(User user) {
        user.id = null;
        user = userRepository.save(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                user,
                null,
                User.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return user;
    }

    @Transactional
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    @Transactional
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Transactional
    public Optional<User> getUserByLoginAndPassword(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password);
    }
}
