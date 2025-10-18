package com.loopus.loopus_be.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "support_chats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupportChat {

    @Id
    @GeneratedValue
    @Column(name = "chat_id", columnDefinition = "UUID")
    private UUID chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user; // người mở chat

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Users admin; // có thể null nếu chưa có admin nhận

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "NOT_YET"; // open, closed
}
