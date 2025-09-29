package com.loopus.loopus_be.model;

import com.loopus.loopus_be.enums.ParticipationStatus;
import com.loopus.loopus_be.model.embedded_key.EventParticipantId;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "event_participants")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipant {

    @EmbeddedId
    private EventParticipantId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private GroupEvent event;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private Users user;

    @Enumerated(EnumType.STRING)
    private ParticipationStatus status;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;
}
