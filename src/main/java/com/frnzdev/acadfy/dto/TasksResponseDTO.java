package com.frnzdev.acadfy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;


public record TasksResponseDTO(UUID id,
                               String title,
                               String description,
                               @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
                               LocalDateTime deliver_work,
                               String difficulty,
                               String priority) {
}
