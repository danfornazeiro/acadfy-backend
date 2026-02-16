package com.frnzdev.acadfy.controller;

import com.frnzdev.acadfy.domain.User;
import com.frnzdev.acadfy.domain.enums.RoleUser;
import com.frnzdev.acadfy.dto.LoginRequestDTO;
import com.frnzdev.acadfy.dto.RegisterRequestDTO;
import com.frnzdev.acadfy.dto.ResponseDTO;
import com.frnzdev.acadfy.infra.security.TokenService;
import com.frnzdev.acadfy.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body){
        User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));
        if(passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.badRequest().build();
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO body){

        if(repository.existsByEmail(body.email())) {
            return ResponseEntity
                    .badRequest()
                    .body("Email já cadastrado");
        }


        if(repository.existsByUsername(body.name())) {
            return ResponseEntity
                    .badRequest()
                    .body("Nome de usuário já existe");
        }

        User newUser = new User();
        newUser.setUsername(body.name());
        newUser.setEmail(body.email());
        newUser.setPassword(passwordEncoder.encode(body.password()));
        newUser.setSchoolName(body.schoolName());


        if(body.roleUser() != null){
            String roleRaw = body.roleUser().toUpperCase().trim();

            if(roleRaw.startsWith("ROLE_")){
                roleRaw = roleRaw.substring(5);
            }

            try {
                newUser.setRoleUser(
                        RoleUser.valueOf(roleRaw)
                );
            } catch (IllegalArgumentException ex){
                newUser.setRoleUser(
                        RoleUser.STUDENT
                );
            }
        } else {
            newUser.setRoleUser(
                    RoleUser.STUDENT
            );
        }


        try {
            repository.save(newUser);
        } catch (Exception e){
            return ResponseEntity
                    .badRequest()
                    .body("Email ou username já existente");
        }

        String token = tokenService.generateToken(newUser);

        return ResponseEntity.ok(
                new ResponseDTO(newUser.getUsername(), token)
        );
    }
}
