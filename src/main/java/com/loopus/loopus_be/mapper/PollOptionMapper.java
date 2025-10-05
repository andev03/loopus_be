package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.PollOptionDto;
import com.loopus.loopus_be.model.PollOption;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PollVoteMapper.class})
public interface PollOptionMapper {
    List<PollOptionDto> toDtoList(List<PollOption> models);

    PollOptionDto toDto(PollOption model);
}
