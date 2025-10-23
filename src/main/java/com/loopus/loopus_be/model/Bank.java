package com.loopus.loopus_be.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "banks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bank {

    @Id
    @GeneratedValue
    @Column(name = "bank_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID bankId;

    @Column(name = "bank_name", length = 100, nullable = false)
    private String bankName;

    @Column(name = "bin_code", length = 20, unique = true, nullable = false)
    private String binCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Quan hệ 1-nhiều: 1 bank có thể có nhiều user liên kết
    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Users> users;
}
