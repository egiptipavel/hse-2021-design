package com.hse.boards.controllers;

import com.hse.boards.models.Board;
import com.hse.boards.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/api/boards")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> createBoard(Board board) {
        boardService.createBoard(board);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Long>> getBoards() {
        return boardService.getBoards()
                .map(boards -> new ResponseEntity<>(boards.stream().map(board -> board.id).collect(Collectors.toList()),
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(List.of(), HttpStatus.OK));
    }

    @PostMapping(value = "/{boardId}/admin/{userId}")
    public ResponseEntity<Void> setAdmin(@PathVariable("boardId") long boardId, @PathVariable("userId") long userId) {
        boardService.setAdmin(boardId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
