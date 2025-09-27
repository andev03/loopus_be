package com.loopus.loopus_be.model;

import com.loopus.loopus_be.enums.SettingTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "settings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Setting {

    @Id
    @GeneratedValue
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id",
            foreignKey = @ForeignKey(name = "fk_settings_user"))
    private Users user;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private SettingTypeEnum type;

    @Column(nullable = false)
    private Boolean enabled = true;
}
