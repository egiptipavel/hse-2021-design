package com.hse.boards.controllers;

import com.hse.boards.models.Board;
import com.hse.boards.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/api/board")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> createBoard(@RequestBody Board board) {
        boardService.createBoard(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable("id") int id) {
        return boardService.getBoardById(id)
                .map(board -> new ResponseEntity<>(board, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
