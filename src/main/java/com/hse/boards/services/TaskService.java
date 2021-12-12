package com.hse.boards.services;

import com.hse.boards.exceptions.ServiceException;
import com.hse.boards.models.BoardToUser;
import com.hse.boards.models.StatusOfTask;
import com.hse.boards.models.Task;
import com.hse.boards.models.User;
import com.hse.boards.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

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
        BoardToUser board = boardService.getBoardToUserByBoardIdAndUserId(task.boardId, user.id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                        "Board with such id does not exist or user is not a member of this board."));
        if (!board.admin) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "User is not an admin of this board.");
        }
        task.id = null;
        taskRepository.save(task);
    }

    @Transactional
    public void setOwnerToTask(long taskId, long ownerId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Task with such id does not exist"));
        BoardToUser board = boardService.getBoardToUserByBoardIdAndUserId(task.boardId, user.id)
                .orElseThrow(() -> new ServiceException(HttpStatus.NOT_FOUND,
                        "Board with such id does not exist or user is not a member of this board."));
        if (!board.admin) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "User is not an admin of this board.");
        }
        task.ownerId = ownerId;
        taskRepository.save(task);
    }

    @Transactional
    public void setTaskForCheckingByOwner(long taskId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ServiceException(HttpStatus.BAD_REQUEST, "Task with such id does not exist"));
        if (task.status != StatusOfTask.OPEN) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "Task not open");
        }
        if (!Objects.equals(task.ownerId, user.id)) {
            throw new ServiceException(HttpStatus.BAD_REQUEST, "User is not the owner of this task");
        }
        task.status = StatusOfTask.FOR_CHECKING;
        taskRepository.save(task);
    }

    @Transactional
    public List<Task> getAllBoardTasks(long boardId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boardService.getBoardToUserByBoardIdAndUserId(boardId, user.id);
        List<Task> tasks = taskRepository.findTasksByBoardId(boardId);
        Timestamp time = new Timestamp(System.currentTimeMillis());
        for (Task task : tasks) {
            if (task.status == StatusOfTask.OPEN && task.deadline.before(time)) {
                task.status = StatusOfTask.CLOSED;
                taskRepository.save(task);
            }
        }
        return tasks;
    }
}
