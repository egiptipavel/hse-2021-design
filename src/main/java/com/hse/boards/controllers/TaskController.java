package com.hse.boards.controllers;

import com.hse.boards.models.ResultOfChecking;
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

    @PostMapping(value = "/{taskId}/{ownerId}")
    public ResponseEntity<Void> setOwnerToTask(@PathVariable("taskId") long taskId,
                                               @PathVariable("ownerId") long ownerId) {
        taskService.setOwnerToTask(taskId, ownerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{taskId}")
    public ResponseEntity<Void> setTaskForCheckingByOwner(@PathVariable("taskId") long taskId) {
        taskService.setTaskForCheckingByOwner(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/{taskId}/admin")
    public ResponseEntity<Void> setResultOfCheckingForTaskByAdmin(@PathVariable("taskId") long taskId,
                                                                  @RequestParam("status") ResultOfChecking result) {
        taskService.setResultOfCheckingForTaskByAdmin(taskId, result);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{boardId}")
    public ResponseEntity<List<Task>> getAllBoardTasks(@PathVariable("boardId") long boardId) {
        return new ResponseEntity<>(taskService.getAllBoardTasks(boardId), HttpStatus.OK);
    }
}
