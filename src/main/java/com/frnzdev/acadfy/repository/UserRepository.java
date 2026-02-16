package com.frnzdev.acadfy.repository;

import com.frnzdev.acadfy.domain.enums.RoleUser;
import com.frnzdev.acadfy.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    List<User> findAllByRoleUser(RoleUser roleUser);


}

