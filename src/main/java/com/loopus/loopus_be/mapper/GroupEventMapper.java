package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.GroupEventDto;
import com.loopus.loopus_be.model.GroupEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {EventParticipantMapper.class, UserMapper.class})
public interface GroupEventMapper {
    List<GroupEventDto> toDtoList(List<GroupEvent> models);

    @Mapping(target = "groupId", source = "group.groupId")
    GroupEventDto toDto(GroupEvent model);
}
