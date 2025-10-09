package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.StoryComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StoryCommentRepository extends JpaRepository<StoryComment, UUID> {
}
