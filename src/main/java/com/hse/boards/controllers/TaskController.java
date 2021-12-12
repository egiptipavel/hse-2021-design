package com.hse.boards.controllers;

import com.hse.boards.models.Task;
import com.hse.boards.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        taskService.addTask(task);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(value = "/{boardId}")
    public ResponseEntity<List<Task>> getBoardTasks(@PathVariable("boardId") long boardId) {
        return taskService.getBoardTasks(boardId)
                .map(tasks -> new ResponseEntity<>(tasks, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
