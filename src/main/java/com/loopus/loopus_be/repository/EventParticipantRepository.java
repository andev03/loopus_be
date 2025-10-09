package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.enums.ParticipationStatus;
import com.loopus.loopus_be.model.EventParticipant;
import com.loopus.loopus_be.model.embedded_key.EventParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, EventParticipantId> {
    List<EventParticipant> findAllByEvent_EventIdAndStatus(UUID eventId, ParticipationStatus eventStatus);
}
