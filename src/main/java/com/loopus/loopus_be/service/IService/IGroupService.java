package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.GroupDto;
import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.dto.request.HandleToGroupRequest;
import com.loopus.loopus_be.dto.request.CreateGroupRequest;

import java.util.List;
import java.util.UUID;

public interface IGroupService {

    List<GroupDto> getAllgroupsByUserId(UUID userId);

    GroupDto createGroup(CreateGroupRequest createGroupRequest);

    GroupDto addMemberToGroup(HandleToGroupRequest handleToGroupRequest);

    GroupDto leaveGroup(HandleToGroupRequest handleToGroupRequest);

    List<UsersDto> viewMembersInGroup(UUID groupId);
}
