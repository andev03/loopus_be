package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.EventParticipantDto;
import com.loopus.loopus_be.model.EventParticipant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,uses = UserMapper.class)
public interface EventParticipantMapper {
    List<EventParticipantDto> toDtoList(List<EventParticipant> models);

    EventParticipantDto toDto(EventParticipant model);
}
