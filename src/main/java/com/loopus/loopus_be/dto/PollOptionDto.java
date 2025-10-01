package com.loopus.loopus_be.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class PollOptionDto {
    private UUID optionId;

    private String optionText;

    private List<PollVoteDto> votes;
}
