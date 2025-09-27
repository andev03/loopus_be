package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.PollVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PollVoteRepository extends JpaRepository<PollVote, UUID> {
}
