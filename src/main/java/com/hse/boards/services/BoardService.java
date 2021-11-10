package com.hse.boards.services;

import com.hse.boards.models.Board;
import com.hse.boards.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component("BoardService")
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public void createBoard(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    public Optional<Board> getBoardById(int id) {
        return boardRepository.findById(id);
    }
}
