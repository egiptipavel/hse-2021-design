package com.hse.boards.repositories;

import com.hse.boards.models.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {
    Optional<List<Board>> findAllByUserId(Integer ownerId);

    Optional<Board> findByIdAndUserId(Integer id, Integer userId);
}
