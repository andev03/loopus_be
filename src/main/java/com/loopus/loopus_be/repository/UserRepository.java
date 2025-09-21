package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByUsernameAndPasswordHash(String username, String password);
    Users findByUsername(String email);
}
