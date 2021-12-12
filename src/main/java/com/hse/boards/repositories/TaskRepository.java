package com.hse.boards.repositories;

import com.hse.boards.models.StatusOfTask;
import com.hse.boards.models.Task;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    List<Task> findTasksByBoardId(long boardId);

    List<Task> findTasksByBoardIdAndStatus(long boardId, StatusOfTask status);
}
