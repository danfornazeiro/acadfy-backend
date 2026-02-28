package com.frnzdev.acadfy.controller;


import com.frnzdev.acadfy.dto.LoginRequestDTO;
import com.frnzdev.acadfy.dto.RegisterRequestDTO;
import com.frnzdev.acadfy.dto.ResponseDTO;
import com.frnzdev.acadfy.infra.security.service.authService.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body){
        ResponseDTO response = authService.login(body);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody RegisterRequestDTO body){
        ResponseDTO response = authService.register(body);
        return ResponseEntity.ok(response);
    }
}
