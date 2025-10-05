package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.EventParticipant;
import com.loopus.loopus_be.model.embedded_key.EventParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantId> {
}
