package com.frnzdev.acadfy.infra.security.service.taskService;

import com.frnzdev.acadfy.domain.Task;
import com.frnzdev.acadfy.domain.User;
import com.frnzdev.acadfy.domain.enums.task.Difficulty;
import com.frnzdev.acadfy.domain.enums.task.Priority;
import com.frnzdev.acadfy.domain.enums.task.Status;
import com.frnzdev.acadfy.dto.TasksRequestDTO;
import com.frnzdev.acadfy.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasksUser(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return taskRepository.findByUser(user);
    }

    public List<Task> getTaskByPriority(Priority priority, Authentication authentication) {
        User user = (User)  authentication.getPrincipal();

        List<Task> tasks = taskRepository.findByUserAndPriority(user, priority);

        if(tasks.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such task");
        }

        return tasks;
    }

    public List<Task> getTaskByDifficulty(Difficulty difficulty, Authentication authentication) {
        User user = (User)  authentication.getPrincipal();

        List<Task> tasks = taskRepository.findByUserAndDifficulty(user, difficulty);


        if(tasks.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such task");
        }
        return tasks;
    }

    public List<Task> getTaskByStatus(Status taskStatus, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        List<Task> tasks =
                taskRepository.findByUserAndStatus(user, taskStatus);

       if(tasks.isEmpty()){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No such task");
       }
       return tasks;
    }

    public Task createTask(TasksRequestDTO body, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        Task newTask = new Task();
        newTask.setTitle(body.title());
        newTask.setDescription(body.description());
        newTask.setDeliver_work(body.deliver_work());
        newTask.setDifficulty(Difficulty.valueOf(body.difficulty()));
        newTask.setPriority(Priority.valueOf(body.priority()));
        newTask.setStatus(Status.valueOf(body.status()));
        newTask.setUser(user);
       return taskRepository.save(newTask);
    }

    public Task updateTask(UUID id, TasksRequestDTO body, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id: " + id + " not found"));

        assert user != null;
        if(!task.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized request.");
        }

        if(body.title() != null){
            task.setTitle(body.title());
        }

        if(body.description() != null){
            task.setDescription(body.description());
        }

        if(body.deliver_work() != null){
            task.setDeliver_work(body.deliver_work());
        }

        if(body.difficulty() != null){
            task.setDifficulty(Difficulty.valueOf(body.difficulty()));
        }

        if(body.priority() != null){
            task.setPriority(Priority.valueOf(body.priority()));
        }

        if(body.status() != null){
            task.setStatus(Status.valueOf(body.status()));
        }

        return taskRepository.save(task);
    }

    public void deleteTask(UUID id, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("Task not found"));

        assert user != null;
        if(!task.getUser().getId().equals(user.getId())){
            throw new RuntimeException("Unauthorized request.");
        }

       taskRepository.delete(task);
    }


}
