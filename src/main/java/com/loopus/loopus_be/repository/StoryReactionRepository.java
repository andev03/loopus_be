package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.StoryReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoryReactionRepository extends JpaRepository<StoryReaction, UUID> {
}
