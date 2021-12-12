package com.hse.boards.services;

import com.hse.boards.exceptions.ServiceException;
import com.hse.boards.models.Board;
import com.hse.boards.models.BoardToUser;
import com.hse.boards.models.User;
import com.hse.boards.repositories.BoardRepository;
import com.hse.boards.repositories.BoardToUserRepository;
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

    private final BoardToUserRepository boardToUserRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository, BoardToUserRepository boardToUserRepository) {
        this.boardRepository = boardRepository;
        this.boardToUserRepository = boardToUserRepository;
    }

    @Transactional
    public void createBoard(Board board) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        board.id = null;
        Board savedBoard = boardRepository.save(board);
        boardToUserRepository.save(new BoardToUser(null, savedBoard.id, user.id, true));
    }

    @Transactional
    public Board getBoardById(int boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                        "Board with such id does not exist or user is not a member of this board."));
    }

    @Transactional
    public Optional<List<Board>> getBoards() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return boardRepository.findAllByUserId(user.id);
    }

    @Transactional
    public Optional<BoardToUser> getBoardToUserByBoardIdAndUserId(Integer boardId, Integer userId) {
        return boardToUserRepository.findByBoardIdAndUserId(boardId, userId);
    }

    @Transactional
    public void setAdmin(int boardId, int userId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BoardToUser boardToUser = getBoardToUserByBoardIdAndUserId(boardId, user.id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                        "Board with such id does not exist or user is not a member of this board."));
        if (!boardToUser.admin) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "User is not an admin of this board");
        }
        boardToUser = getBoardToUserByBoardIdAndUserId(boardId, userId)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                        "This user is not a member of this board."));
        if (boardToUser.admin) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Given user is already admin of this board.");
        }
        boardToUser.admin = true;
        boardToUserRepository.save(boardToUser);
    }
}
