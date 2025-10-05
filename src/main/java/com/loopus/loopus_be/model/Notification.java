package com.loopus.loopus_be.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notification_id", columnDefinition = "UUID")
    private UUID notificationId;

    // Người nhận thông báo
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_notifications_user"))
    private Users user;

    // Người gửi / người liên quan
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "fk_notifications_sender"))
    private Users sender;

    // Nhóm liên quan (nếu có)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_notifications_group"))
    private Group group;

    @Column(nullable = false, length = 50)
    private String type; // PAYMENT_RECEIVED, PAYMENT_REMINDER, COMMENT, INVITE

    @Column(length = 255)
    private String title; // Tiêu đề ngắn (VD: "Lê Anh đã trả bạn 100.000đ")

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message; // Nội dung chi tiết

    @Column(precision = 15, scale = 2)
    private BigDecimal amount; // Số tiền liên quan (nếu có)

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt = Instant.now();

    // Cập nhật tự động thời gian updated_at khi entity thay đổi
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now();
    }
}