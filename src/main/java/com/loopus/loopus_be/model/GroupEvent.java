package com.loopus.loopus_be.model;

import com.loopus.loopus_be.enums.EventRepeat;
import com.loopus.loopus_be.enums.EventStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "group_events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupEvent {

    @Id
    @GeneratedValue
    @Column(name = "event_id", columnDefinition = "UUID")
    private UUID eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", nullable = false)
    private Users creator;

    private String title;

    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDate eventDate;

    @Column(name = "event_time", nullable = false)
    private LocalTime eventTime;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EventStatus status = EventStatus.PENDING;

    // Lặp lại sự kiện
    @Column(name = "repeat_type", nullable = false)
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private EventRepeat repeatType = EventRepeat.NONE;

    @Column(name = "created_at", updatable = false, insertable = false,
            columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EventParticipant> participants = new HashSet<>();
}