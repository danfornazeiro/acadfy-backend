package com.frnzdev.acadfy.domain;

import com.frnzdev.acadfy.domain.enums.task.Difficulty;
import com.frnzdev.acadfy.domain.enums.task.Priority;
import com.frnzdev.acadfy.domain.enums.task.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "task")
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String description;
    private LocalDateTime deliver_work;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @Enumerated(EnumType.STRING)
    private Status status;
    private String tag;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;



}
