package com.loopus.loopus_be.model;

import com.loopus.loopus_be.enums.MessageTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "group_chats")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupChat {

    @Id
    @GeneratedValue
    @Column(name = "chat_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID chatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private Users sender;

    @Column(name = "message", columnDefinition = "text", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 50, nullable = false)
    @Builder.Default
    private MessageTypeEnum type = MessageTypeEnum.TEXT;

    @Column(name = "image_url", columnDefinition = "text")
    private String imageUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;
}