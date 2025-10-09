package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.StoryReactionDto;
import com.loopus.loopus_be.model.StoryReaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface StoryReactionMapper {
    List<StoryReactionDto> toDtoList(List<StoryReaction> models);

    StoryReactionDto toDto(StoryReaction model);
}
