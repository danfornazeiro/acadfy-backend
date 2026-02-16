package com.frnzdev.acadfy.controller;

import com.frnzdev.acadfy.domain.*;
import com.frnzdev.acadfy.domain.enums.Difficulty;
import com.frnzdev.acadfy.domain.enums.Priority;
import com.frnzdev.acadfy.domain.enums.Status;
import com.frnzdev.acadfy.dto.TasksRequestDTO;
import com.frnzdev.acadfy.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TasksController {



    @Autowired
    private TaskRepository  taskRepository;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TasksRequestDTO body, Authentication authentication) {

        User user = (User) authentication.getPrincipal();


        Task newTask = new Task();
        newTask.setTitle(body.title());
        newTask.setDescription(body.description());
        newTask.setDeliver_work(body.deliver_work());
        newTask.setDifficulty(Difficulty.valueOf(body.difficulty()));
        newTask.setPriority(Priority.valueOf(body.priority()));
        newTask.setStatus(Status.valueOf(body.status()));
        newTask.setUser(user);
        taskRepository.save(newTask);
        return ResponseEntity.ok().build();

    }

}
