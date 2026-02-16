package com.frnzdev.acadfy.repository;

import com.frnzdev.acadfy.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
}
