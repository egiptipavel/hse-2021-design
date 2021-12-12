package com.hse.boards.repositories;

import com.hse.boards.models.BoardToUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BoardToUserRepository extends CrudRepository<BoardToUser, Long> {
    Optional<BoardToUser> findBoardsByBoardIdAndUserId(long id, long userId);
}
