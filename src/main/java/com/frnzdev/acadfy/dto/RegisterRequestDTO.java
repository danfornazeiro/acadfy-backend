package com.frnzdev.acadfy.dto;

public record RegisterRequestDTO(String name,
                                 String email,
                                 String password,
                                 String schoolName,
                                 String roleUser) {
}
