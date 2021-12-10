package com.hse.boards.services;

import com.hse.boards.exceptions.ServiceException;
import com.hse.boards.models.Board;
import com.hse.boards.models.User;
import com.hse.boards.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component("BoardService")
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public void createBoard() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = new Board(null, user.id, true);
        boardRepository.save(board);
    }

    @Transactional
    public Optional<Board> getBoardByIdAndUserId(int id, int userId) {
        return boardRepository.findByIdAndUserId(id, userId);
    }

    @Transactional
    public Optional<List<Board>> getBoards() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return boardRepository.findAllByUserId(user.id);
    }

    @Transactional
    public void setAdmin(int board_id, int user_id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = getBoardByIdAndUserId(board_id, user.id).orElseThrow(
                () -> new ServiceException(HttpStatus.NOT_FOUND,
                        "Board with such id does not exist or user is not a member of this board."));
        if (!board.admin) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "User is not an admin of this board");
        }
        board = getBoardByIdAndUserId(board_id, user_id).orElseThrow(
                () -> new ServiceException(HttpStatus.NOT_FOUND, "Given user is not a member of this board."));
        if (board.admin) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Given user is already admin of this board.");
        }
        board.admin = true;
        boardRepository.save(board);
    }
}
