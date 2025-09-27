package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.PollOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PollOptionRepository extends JpaRepository<PollOption, UUID> {
}
