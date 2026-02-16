package com.frnzdev.acadfy.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record TasksRequestDTO(
                              String title,
                              String description,
                              LocalDateTime deliver_work,
                              String difficulty,
                              String priority,
                              String status) {
}
