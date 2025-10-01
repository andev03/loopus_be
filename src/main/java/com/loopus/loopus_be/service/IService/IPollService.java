package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.PollDto;
import com.loopus.loopus_be.dto.PollOptionDto;
import com.loopus.loopus_be.dto.PollVoteDto;
import com.loopus.loopus_be.dto.request.AddOptionRequest;
import com.loopus.loopus_be.dto.request.CreatePollRequest;
import com.loopus.loopus_be.dto.request.VotePollRequest;

import java.util.List;
import java.util.UUID;

public interface IPollService {
    List<PollDto> getPollsByGroupId(UUID groupId);

    PollDto getPollById(UUID pollId);

    void deletePollById(UUID pollId);

    PollDto createPoll(CreatePollRequest pollRequest);

    PollOptionDto addOptionToPoll(AddOptionRequest pollRequest);

    PollVoteDto votePoll(VotePollRequest votePollRequest);
}
