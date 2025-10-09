package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.StoryDto;
import com.loopus.loopus_be.model.Story;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface StoryMapper {
    List<StoryDto> toDtoList(List<Story> models);

    StoryDto toDto(Story model);
}
