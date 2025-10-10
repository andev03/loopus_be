package com.loopus.loopus_be.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "group_albums")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupAlbum {

    @Id
    @GeneratedValue
    @Column(name = "album_id", updatable = false, nullable = false)
    private UUID albumId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Users createdBy;

    @Column(name = "created_at", columnDefinition = "TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP")
    @Builder.Default
    private OffsetDateTime createdAt = OffsetDateTime.now();
}
