package com.frnzdev.acadfy.repository;

import com.frnzdev.acadfy.domain.Task;
import com.frnzdev.acadfy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByUser(User user);

}
