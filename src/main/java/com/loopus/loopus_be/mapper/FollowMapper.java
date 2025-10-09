package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.FollowDto;
import com.loopus.loopus_be.model.Follow;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface FollowMapper {
    FollowDto toDto(Follow model);

    List<FollowDto> toDtoList(List<Follow> models);
}
