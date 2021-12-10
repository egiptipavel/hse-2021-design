package com.hse.boards.repositories;

import com.hse.boards.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {
    Optional<List<Task>> findAllByBoardId(Integer boardId);
}
