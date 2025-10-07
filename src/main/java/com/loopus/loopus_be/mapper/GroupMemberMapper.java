package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.GroupMemberDto;
import com.loopus.loopus_be.model.GroupMember;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = UserMapper.class)
public interface GroupMemberMapper {
    List<GroupMemberDto> toDtoList(List<GroupMember> models);

    GroupMemberDto toDto(GroupMember model);
}
