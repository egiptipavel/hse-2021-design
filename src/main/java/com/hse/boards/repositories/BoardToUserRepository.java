package com.hse.boards.repositories;

import com.hse.boards.models.Board;
import com.hse.boards.models.BoardToUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BoardToUserRepository extends CrudRepository<BoardToUser, Integer> {
    Optional<BoardToUser> findByBoardIdAndUserId(Integer id, Integer userId);

}
