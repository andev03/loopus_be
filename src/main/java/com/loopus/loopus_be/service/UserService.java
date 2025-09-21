package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.dto.request.LoginRequest;
import com.loopus.loopus_be.dto.request.RegisterRequest;
import com.loopus.loopus_be.exception.LoginException;
import com.loopus.loopus_be.mapper.UserMapper;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UsersDto login(String username, String password) {
        Users user = userRepository.findByUsernameAndPasswordHash(username, password);

        if (user == null) {
            throw new LoginException("Invalid username or password");
        }

        return userMapper.toDto(user);
    }

    @Override
    public UsersDto register(RegisterRequest request) {
        Users userCheck = userRepository.findByUsername(request.getEmail());
        if(userCheck != null) {
            throw new LoginException("Email đã tồn tại!");
        }

        Users userSave = userRepository.save(Users.builder()
                        .username(request.getEmail())
                        .passwordHash(request.getPassword())
                        .createdAt(LocalDateTime.now())
                        .fullName(request.getFirstName() + " " + request.getLastName())
                        .dateOfBirth((request.getDob()))
                .build());
        return userMapper.toDto(userSave);
    }
}
