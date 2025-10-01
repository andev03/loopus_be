package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.PollDto;
import com.loopus.loopus_be.dto.PollOptionDto;
import com.loopus.loopus_be.dto.PollVoteDto;
import com.loopus.loopus_be.dto.request.AddOptionRequest;
import com.loopus.loopus_be.dto.request.CreatePollRequest;
import com.loopus.loopus_be.dto.request.VotePollRequest;
import com.loopus.loopus_be.mapper.PollMapper;
import com.loopus.loopus_be.mapper.PollOptionMapper;
import com.loopus.loopus_be.mapper.PollVoteMapper;
import com.loopus.loopus_be.model.Poll;
import com.loopus.loopus_be.model.PollOption;
import com.loopus.loopus_be.model.PollVote;
import com.loopus.loopus_be.repository.*;
import com.loopus.loopus_be.service.IService.IPollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PollService implements IPollService {

    private final PollRepository pollRepository;
    private final PollMapper pollMapper;
    private final PollOptionRepository pollOptionRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final PollOptionMapper pollOptionMapper;
    private final PollVoteRepository pollVoteRepository;
    private final PollVoteMapper pollVoteMapper;

    @Override
    public List<PollDto> getPollsByGroupId(UUID groupId) {
        return pollMapper.toDtoList(pollRepository.findByGroup_GroupId(groupId));
    }

    @Override
    public PollDto getPollById(UUID pollId) {
        return pollMapper.toDto(pollRepository.getReferenceById(pollId));
    }

    @Override
    public void deletePollById(UUID pollId) {
        pollRepository.deleteById(pollId);
    }

    @Override
    public PollDto createPoll(CreatePollRequest pollRequest) {
        List<PollOption> options = new ArrayList<>();

        Poll poll = pollRepository.save(Poll.builder()
                .group(groupRepository.getReferenceById(pollRequest.getGroupId()))
                .createdBy(userRepository.getReferenceById(pollRequest.getUserId()))
                .build()
        );

        for (String pollOption : pollRequest.getOptions()) {
            options.add(pollOptionRepository.save(
                    PollOption.builder()
                            .poll(poll)
                            .optionText(pollOption)
                            .build()
            ));
        }

        poll.setOptions(options);

        return pollMapper.toDto(poll);
    }

    @Override
    public PollOptionDto addOptionToPoll(AddOptionRequest pollRequest) {
        PollOption optionDto = pollOptionRepository.save(
                PollOption.builder()
                        .poll(pollRepository.getReferenceById(pollRequest.getPollId()))
                        .optionText(pollRequest.getOptionText())
                        .build()
        );

        return pollOptionMapper.toDto(optionDto);
    }

    @Override
    public PollVoteDto votePoll(VotePollRequest votePollRequest) {
        PollVote pollVote = pollVoteRepository.save(
                PollVote.builder()
                        .user(userRepository.getReferenceById(votePollRequest.getUserId()))
                        .option(pollOptionRepository.getReferenceById(votePollRequest.getOptionId()))
                        .poll(pollRepository.getReferenceById(votePollRequest.getPollId()))
                        .build()
        );

        return pollVoteMapper.toDto(pollVote);
    }
}
