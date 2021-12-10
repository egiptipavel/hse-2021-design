package com.hse.boards.services;

import com.hse.boards.exceptions.ServiceException;
import com.hse.boards.models.Board;
import com.hse.boards.models.Task;
import com.hse.boards.models.User;
import com.hse.boards.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Component("TaskService")
public class TaskService {
    private final TaskRepository taskRepository;

    private final BoardService boardService;

    @Autowired
    public TaskService(TaskRepository taskRepository, BoardService boardService) {
        this.taskRepository = taskRepository;
        this.boardService = boardService;
    }

    @Transactional
    public void addTask(Task task) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Board board = boardService.getBoardByIdAndUserId(task.boardId, user.id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "User does not have access to this board"));
        if (!board.admin) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "User is not an admin of this board.");
        }
        task.id = null;
        taskRepository.save(task);
    }

    @Transactional
    public Optional<List<Task>> getBoardTasks(int boardId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boardService.getBoardByIdAndUserId(boardId, user.id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND, "User does not have access to this board"));
        return taskRepository.findAllByBoardId(boardId);
    }
}
