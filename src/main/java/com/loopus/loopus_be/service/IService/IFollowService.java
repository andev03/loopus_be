package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.FollowDto;
import com.loopus.loopus_be.dto.UsersDto;

import java.util.List;
import java.util.UUID;

public interface IFollowService {
    UsersDto followUser(UUID userId, UUID targetUserId);

    List<UsersDto> getFollowersByUserId(UUID userId);

    List<UsersDto> getFollowingByUserId(UUID userId);

    FollowDto checkFollowedYet(UUID userId, UUID targetUserId);

    void unfollowUser(UUID userId, UUID targetUserId);
}
