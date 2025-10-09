package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.StoryCommentDto;
import com.loopus.loopus_be.model.StoryComment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface StoryCommentMapper {
    List<StoryCommentDto> toDtoList(List<StoryComment> models);

    StoryCommentDto toDto(StoryComment model);
}
