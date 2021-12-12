package com.hse.boards.services;

import com.hse.boards.exceptions.ServiceException;
import com.hse.boards.models.User;
import com.hse.boards.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

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
    public User getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "User with such id dost not exist"));
    }

    @Transactional
    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "User with this login does not exist"));
    }

    @Transactional
    public User getUserByLoginAndPassword(String login, String password) {
        return userRepository.findUserByLoginAndPassword(login, password)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                        "User with this login and password does not exist or the password is incorrect"));
    }
}
