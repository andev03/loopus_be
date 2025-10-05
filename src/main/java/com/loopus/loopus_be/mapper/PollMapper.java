package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.PollDto;
import com.loopus.loopus_be.model.Poll;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {PollOptionMapper.class, UserMapper.class, GroupMapper.class})
public interface PollMapper {
    List<PollDto> toDtoList(List<Poll> models);

    PollDto toDto(Poll model);
}
