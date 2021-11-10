package com.hse.boards.services;

import com.hse.boards.models.Task;
import com.hse.boards.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Optional;

@Component("TaskService")
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Transactional
    public void addTask(Task task) {
        taskRepository.save(task);
    }

    @Transactional
    public Optional<Task> getTaskById(int id) {
        return taskRepository.findById(id);
    }
}
