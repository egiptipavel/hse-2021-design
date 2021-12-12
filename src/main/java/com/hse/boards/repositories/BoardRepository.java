package com.hse.boards.repositories;

import com.hse.boards.models.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {
    @Query("SELECT b1.id, b1.name, b1.description FROM board_to_user b1 join board b2 on b1.boardId = b2.id where b1.userId = ?1")
    Optional<List<Board>> findAllByUserId(Integer userId);
}
