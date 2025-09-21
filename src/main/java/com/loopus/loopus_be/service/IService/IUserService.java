package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.dto.request.RegisterRequest;

public interface IUserService {
    UsersDto login(String username, String password);

    UsersDto register(RegisterRequest request);
}
