package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.StoryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryCommentRepository extends JpaRepository<StoryComment, UUID> {
    List<StoryComment> findAllByStory_StoryIdOrderByCreatedAtDesc(UUID storyId);
}
