package com.loopus.loopus_be.model.embedded_key;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberId implements Serializable {

    private UUID groupId;
    private UUID userId;
}
