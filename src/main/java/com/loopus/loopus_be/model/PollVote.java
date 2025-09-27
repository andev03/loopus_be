package com.loopus.loopus_be.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "poll_votes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollVote {

    @Id
    @GeneratedValue
    @Column(name = "vote_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID voteId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = false)
    private PollOption option;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @CreationTimestamp
    @Column(name = "voted_at", updatable = false)
    private OffsetDateTime votedAt;
}
