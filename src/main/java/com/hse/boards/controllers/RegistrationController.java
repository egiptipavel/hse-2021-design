package com.hse.boards.controllers;

import com.hse.boards.models.User;
import com.hse.boards.security.JwtProvider;
import com.hse.boards.services.BoardService;
import com.hse.boards.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {
    private final UserService userService;

    private final BoardService boardService;

    private final JwtProvider jwtProvider;

    @Autowired
    public RegistrationController(UserService userService, BoardService boardService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.boardService = boardService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(value = "/reg")
    public ResponseEntity<Void> registration(@RequestBody User user) {
        user = userService.createUser(user);
        boardService.createBoard();
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtProvider.generateToken(user.login))
                .build();
    }
}