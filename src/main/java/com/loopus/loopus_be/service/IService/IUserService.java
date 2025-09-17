package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.UsersDto;

public interface IUserService {
    UsersDto login(String username, String password);
}
