package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Follow;
import com.loopus.loopus_be.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, UUID> {
    List<Follow> findAllByFollower(Users user);

    List<Follow> findAllByFollowing(Users user);

    Follow findAllByFollowerAndFollowing(Users target, Users user);

    List<Follow> findAllByFollower_UserId(UUID userId);
}
