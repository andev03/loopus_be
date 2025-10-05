package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.enums.FeedbackType;
import com.loopus.loopus_be.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    List<Feedback> findAllByType(FeedbackType type);
}
