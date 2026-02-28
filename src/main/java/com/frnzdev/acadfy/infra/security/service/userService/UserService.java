package com.frnzdev.acadfy.infra.security.service.userService;

import com.frnzdev.acadfy.domain.User;
import com.frnzdev.acadfy.domain.enums.RoleUser;
import com.frnzdev.acadfy.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public List<User> getUsersByRole(RoleUser roleUser){
        return userRepository.findAllByRoleUser(roleUser);
    }
}
