package com.frnzdev.acadfy.controller;

import com.frnzdev.acadfy.domain.*;
import com.frnzdev.acadfy.dto.TasksRequestDTO;
import com.frnzdev.acadfy.infra.security.service.taskService.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TasksController {

    private final TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TasksRequestDTO body,Authentication authentication) {
         Task newTask = taskService.createTask(body, authentication);
         return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
    }

    @GetMapping("/me")
    public List<Task> meTasks(Authentication authentication){
        return taskService.getTasksUser(authentication);
    }

    @PatchMapping("/{id}")
    public Task updateTask(@PathVariable UUID id, @RequestBody TasksRequestDTO body, Authentication authentication) {
        return taskService.updateTask(id, body, authentication);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable UUID id,  Authentication authentication) {

       taskService.deleteTask(id, authentication);

        return ResponseEntity.ok().build();
    }
}
