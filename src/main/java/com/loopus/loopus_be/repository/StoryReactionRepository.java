package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.StoryReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoryReactionRepository extends JpaRepository<StoryReaction, UUID> {
    List<StoryReaction> findAllByStory_StoryIdOrderByCreatedAtDesc(UUID storyId);
}
