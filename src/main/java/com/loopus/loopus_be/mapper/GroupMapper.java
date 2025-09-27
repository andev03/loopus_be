package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.GroupDto;
import com.loopus.loopus_be.model.Group;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {
    List<GroupDto> toDtoList(List<Group> models);

    GroupDto toDto(Group model);
}
