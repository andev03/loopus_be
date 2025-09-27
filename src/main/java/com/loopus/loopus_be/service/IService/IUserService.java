package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.dto.request.RegisterRequest;
import com.loopus.loopus_be.dto.request.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface IUserService {
    UsersDto login(String username, String password);

    UsersDto register(RegisterRequest request);

    void otpForgotPassword(String email);

    UsersDto findUserByEmail(String email);

    UsersDto updateAvatar(UUID userId, MultipartFile file);

    UsersDto updateInformation(UpdateUserRequest request);
}
