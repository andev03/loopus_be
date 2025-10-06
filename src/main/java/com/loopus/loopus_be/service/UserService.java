package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.dto.request.RegisterRequest;
import com.loopus.loopus_be.dto.request.UpdateUserRequest;
import com.loopus.loopus_be.enums.UserStatusEnum;
import com.loopus.loopus_be.exception.LoginException;
import com.loopus.loopus_be.mapper.UserMapper;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IEmailService emailService;
    private final IOtpService otpService;
    private final IFileService fileService;
    private final ISettingService settingService;

    @Override
    public UsersDto login(String username, String password) {
        Users user = userRepository.findByUsernameAndPasswordHash(username, password);

        if (user == null) {
            throw new LoginException("Sai tên đăng nhập hoặc mật khẩu!");
        } else if (user.getStatus().equals(UserStatusEnum.PENDING)) {
            throw new LoginException("Tài khoản chưa được xác thực!");
        } else if (user.getStatus().equals(UserStatusEnum.INACTIVE)) {
            throw new LoginException("Tài khoản không hoạt động!");
        } else if (user.getStatus().equals(UserStatusEnum.BANNED)) {
            throw new LoginException("Tài khoản đã bị khoá!");
        }

        return userMapper.toDto(user);
    }

    @Override
    @Transactional
    public UsersDto register(RegisterRequest request) {
        Users userCheck = userRepository.findByUsername(request.getEmail());

        if (userCheck != null) {
            throw new LoginException("Email đã tồn tại!");
        }

        emailService.sendOtpEmail(request.getEmail(), otpService.generateOtp(request.getEmail()));

        Users userSave = userRepository.save(Users.builder()
                .username(request.getEmail())
                .passwordHash(request.getPassword())
                .createdAt(LocalDateTime.now())
                .fullName(request.getFirstName() + " " + request.getLastName())
                .dateOfBirth((request.getDob()))
                .build());

        settingService.initializeSettingsForNewUser(userSave.getUserId());

        return userMapper.toDto(userSave);
    }

    @Override
    public void otpForgotPassword(String email) {
        Users userCheck = userRepository.findByUsername(email);

        if (userCheck == null) {
            throw new LoginException("Email không tồn tại");
        }

        emailService.sendOtpEmail(email, otpService.generateOtp(email));
    }

    @Override
    public UsersDto findUserByEmail(String email) {
        return userMapper.toDto(userRepository.findByUsername(email));
    }

    @Override
    @Transactional
    public UsersDto updateAvatar(UUID userId, MultipartFile file) {
        String imageUrl = fileService.uploadFileUrl(file);

        Users user = userRepository.getReferenceById(userId);

        user.setAvatarUrl(imageUrl);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    @Transactional
    public UsersDto updateInformation(UpdateUserRequest request) {
        Users user = userRepository.getReferenceById(request.getUserId());
        user.setFullName(request.getFirstName() + " " + request.getLastName());
        user.setDateOfBirth(request.getDob());
        user.setBio(request.getBio());

        return userMapper.toDto(userRepository.save(user));
    }
}
