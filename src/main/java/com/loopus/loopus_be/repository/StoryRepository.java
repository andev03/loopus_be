package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoryRepository extends JpaRepository<Story, UUID> {
}
