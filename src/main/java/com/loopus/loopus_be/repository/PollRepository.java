package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PollRepository extends JpaRepository<Poll, UUID> {
}
