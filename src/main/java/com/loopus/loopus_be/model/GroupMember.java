package com.loopus.loopus_be.model;

import com.loopus.loopus_be.enums.RoleEnum;
import com.loopus.loopus_be.model.embedded_key.GroupMemberId;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "group_members")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupMember {

    @EmbeddedId
    private GroupMemberId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50, nullable = false)
    @Builder.Default
    private RoleEnum role = RoleEnum.MEMBER;

    @CreationTimestamp
    @Column(name = "joined_at", updatable = false)
    private OffsetDateTime joinedAt;

    // Quan hệ (optional, nếu muốn mapping với User và Group)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId") // ánh xạ vào groupId của composite key
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId") // ánh xạ vào userId của composite key
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
}
