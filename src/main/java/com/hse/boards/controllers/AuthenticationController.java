package com.hse.boards.controllers;

import com.hse.boards.security.JwtProvider;
import com.hse.boards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    private final UserService userService;

    private final JwtProvider jwtProvider;

    @Autowired
    public AuthenticationController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping(value = "/auth")
    public ResponseEntity<String> authentication(@RequestParam String login, @RequestParam String password) {
        return userService.getUserByLoginAndPassword(login, password)
                .map(user -> ResponseEntity.ok()
                        .body(jwtProvider.generateToken(user.login)))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}