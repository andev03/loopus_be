package com.loopus.loopus_be.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "story_comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryComment {

    @Id
    @GeneratedValue
    @Column(name = "comment_id", columnDefinition = "UUID")
    private UUID commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id", nullable = false)
    private Story story;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP", updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
