package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.PollVoteDto;
import com.loopus.loopus_be.model.PollVote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface PollVoteMapper {
    List<PollVoteDto> toDtoList(List<PollVote> models);

    PollVoteDto toDto(PollVote model);
}
