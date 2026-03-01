package com.frnzdev.acadfy.service.authService;

import com.frnzdev.acadfy.domain.User;
import com.frnzdev.acadfy.domain.enums.RoleUser;
import com.frnzdev.acadfy.dto.LoginRequestDTO;
import com.frnzdev.acadfy.dto.RegisterRequestDTO;
import com.frnzdev.acadfy.dto.ResponseDTO;
import com.frnzdev.acadfy.infra.security.TokenService;
import com.frnzdev.acadfy.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public ResponseDTO login(LoginRequestDTO body){
        User user = this.userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(body.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        String token = tokenService.generateToken(user);
        return new ResponseDTO(user.getUsername(), token);
    }

    public ResponseDTO register(RegisterRequestDTO body){
        if(userRepository.existsByEmail(body.email())) {
           throw new ResponseStatusException(
                   HttpStatus.BAD_REQUEST, "email already exists.");
        }


        if(userRepository.existsByUsername(body.name())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "username already exists."
            );
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
                newUser.setRoleUser(RoleUser.valueOf(roleRaw));
            } catch (IllegalArgumentException ex){
                newUser.setRoleUser(
                        RoleUser.STUDENT
                );
            }
        } else {
            newUser.setRoleUser(RoleUser.STUDENT);
        }
        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);
        return new ResponseDTO(newUser.getUsername(), token);
    }

}
