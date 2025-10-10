package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {
    List<Story> findAllByUser_UserIdInOrAlbum_Group_GroupIdInOrderByCreatedAtDesc(List<UUID> followedUserIds, List<UUID> groupIds);

    List<Story> findAllByAlbum_AlbumIdOrderByCreatedAtDesc(UUID albumId);

    List<Story> findAllByUser_UserIdOrderByCreatedAtDesc(UUID userId);
}
