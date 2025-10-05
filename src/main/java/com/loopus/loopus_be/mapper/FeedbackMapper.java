package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.FeedbackDto;
import com.loopus.loopus_be.model.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface FeedbackMapper {
    FeedbackDto toDto(Feedback model);

    List<FeedbackDto> toDtoList(List<Feedback> models);
}
