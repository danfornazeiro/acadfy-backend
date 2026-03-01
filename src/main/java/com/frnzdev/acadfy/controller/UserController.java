package com.frnzdev.acadfy.controller;

import com.frnzdev.acadfy.domain.enums.RoleUser;
import com.frnzdev.acadfy.domain.User;
import com.frnzdev.acadfy.service.userService.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

   private final UserService userService;
   public UserController(UserService userService) {
       this.userService = userService;
   }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable RoleUser role){
        return userService.getUsersByRole(role);
    }

    @GetMapping("/me")
    public ResponseEntity<User> me(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(user);
    }


}
