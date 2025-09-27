package com.loopus.loopus_be.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "poll_options")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PollOption {

    @Id
    @GeneratedValue
    @Column(name = "option_id", columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @Column(name = "option_text", columnDefinition = "text", nullable = false)
    private String optionText;

    @OneToMany(mappedBy = "option", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PollVote> votes;
}
