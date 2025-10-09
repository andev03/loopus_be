package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.FollowDto;
import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.exception.UsersException;
import com.loopus.loopus_be.mapper.FollowMapper;
import com.loopus.loopus_be.mapper.UserMapper;
import com.loopus.loopus_be.model.Follow;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.FollowRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowService implements IFollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FollowMapper followMapper;

    @Override
    @Transactional
    public UsersDto followUser(UUID userId, UUID targetUserId) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new UsersException("Khong tìm thấy user " + userId));

        Users target = userRepository.findById(targetUserId).orElseThrow(() -> new UsersException("Khong tìm thấy user " + targetUserId));

        return userMapper.toDto(followRepository.save(
                Follow.builder()
                        .follower(user)
                        .following(target)
                        .build()
        ).getFollowing());
    }

    @Override
    public List<UsersDto> getFollowersByUserId(UUID userId) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new UsersException("Khong tìm thấy user " + userId));

        List<Follow> follows = followRepository.findAllByFollowing(user);

        List<Users> users = new ArrayList<>();

        for (Follow follow : follows) {
            users.add(follow.getFollower());
        }

        return userMapper.toDtoList(users);
    }

    @Override
    public List<UsersDto> getFollowingByUserId(UUID userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new UsersException("Khong tìm thấy user " + userId));

        List<Follow> follows = followRepository.findAllByFollower(user);

        List<Users> users = new ArrayList<>();

        for (Follow follow : follows) {
            users.add(follow.getFollowing());
        }

        return userMapper.toDtoList(users);
    }

    @Override
    public FollowDto checkFollowedYet(UUID userId, UUID targetUserId) {

        Users user = userRepository.findById(userId).orElseThrow(() -> new UsersException("Khong tìm thấy user " + userId));

        Users target = userRepository.findById(targetUserId).orElseThrow(() -> new UsersException("Khong tìm thấy user " + targetUserId));

        Follow follow = followRepository.findAllByFollowerAndFollowing(user, target);

        if (follow == null) {
            return followMapper.toDto(
                    Follow.builder().build()
            );
        }

        return followMapper.toDto(follow);
    }

    @Override
    @Transactional
    public void unfollowUser(UUID userId, UUID targetUserId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new UsersException("Khong tìm thấy user " + userId));

        Users target = userRepository.findById(targetUserId).orElseThrow(() -> new UsersException("Khong tìm thấy user " + targetUserId));

        Follow follow = followRepository.findAllByFollowerAndFollowing(user, target);

        if (follow != null) {
            followRepository.delete(follow);
        }
    }
}
