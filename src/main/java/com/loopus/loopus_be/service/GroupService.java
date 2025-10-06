package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.GroupDto;
import com.loopus.loopus_be.dto.UsersDto;
import com.loopus.loopus_be.dto.request.CreateGroupRequest;
import com.loopus.loopus_be.dto.request.CreateNotificationRequest;
import com.loopus.loopus_be.dto.request.HandleToGroupRequest;
import com.loopus.loopus_be.dto.request.UpdateGroupRequest;
import com.loopus.loopus_be.enums.RoleEnum;
import com.loopus.loopus_be.exception.GroupException;
import com.loopus.loopus_be.mapper.GroupMapper;
import com.loopus.loopus_be.mapper.UserMapper;
import com.loopus.loopus_be.model.Group;
import com.loopus.loopus_be.model.GroupMember;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.model.embedded_key.GroupMemberId;
import com.loopus.loopus_be.repository.GroupMemberRepository;
import com.loopus.loopus_be.repository.GroupRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IFileService;
import com.loopus.loopus_be.service.IService.IGroupService;
import com.loopus.loopus_be.service.IService.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GroupService implements IGroupService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IFileService fileService;
    private final INotificationService iNotificationService;

    @Override
    public List<GroupDto> getAllgroupsByUserId(UUID userId) {
        List<GroupMember> groupMembers = groupMemberRepository.findAllById_UserId(userId);

        return groupMembers.stream().map(groupMember -> {
            GroupDto groupDto = new GroupDto();
            groupDto.setGroupId(groupMember.getGroup().getGroupId());
            groupDto.setName(groupMember.getGroup().getName());
            groupDto.setDescription(groupMember.getGroup().getDescription());
            groupDto.setAvatarUrl(groupMember.getGroup().getAvatarUrl());
            groupDto.setCreatedBy(groupMember.getGroup().getCreatedBy());
            groupDto.setCreatedAt(groupMember.getGroup().getCreatedAt());
            return groupDto;
        }).toList();
    }

    @Override
    @Transactional
    public GroupDto createGroup(CreateGroupRequest createGroupRequest) {
        if (createGroupRequest.getUserMemberIds().isEmpty()) {
            throw new GroupException("Một group cân ít nhất 1 thành viên");
        }

        Group createdGroup = groupRepository.save(
                Group.builder()
                        .name(createGroupRequest.getName())
                        .description(createGroupRequest.getDescription())
                        .avatarUrl(createGroupRequest.getAvatarUrl())
                        .createdBy(createGroupRequest.getCreatedBy())
                        .build());


        List<GroupMember> members = createGroupRequest.getUserMemberIds().stream()
                .map(memberId -> {
                    Users member = userRepository.findById(memberId)
                            .orElseThrow(() -> new RuntimeException("User not found"));
                    return GroupMember.builder()
                            .id(new GroupMemberId(createdGroup.getGroupId(), memberId))
                            .group(createdGroup)
                            .user(member)
                            .role(RoleEnum.MEMBER)
                            .build();
                })
                .toList();

        groupMemberRepository.saveAll(members);

        groupMemberRepository.save(
                GroupMember.builder()
                        .id(new GroupMemberId(createdGroup.getGroupId(), createGroupRequest.getCreatedBy()))
                        .group(createdGroup)
                        .user(userRepository.getReferenceById(createGroupRequest.getCreatedBy()))
                        .role(RoleEnum.ADMIN)
                        .build()
        );

        return groupMapper.toDto(createdGroup);
    }

    @Override
    @Transactional
    public GroupDto addMemberToGroup(HandleToGroupRequest handleToGroupRequest) {
        if (groupMemberRepository.existsById(new GroupMemberId(handleToGroupRequest.getGroupId(), handleToGroupRequest.getUserId()))) {
            throw new GroupException("Thành viên đã có trong nhóm");
        }

        GroupMember groupMember = groupMemberRepository.save(
                GroupMember.builder()
                        .id(new GroupMemberId(handleToGroupRequest.getGroupId(), handleToGroupRequest.getUserId()))
                        .group(groupRepository.getReferenceById(handleToGroupRequest.getGroupId()))
                        .user(userRepository.getReferenceById(handleToGroupRequest.getUserId()))
                        .role(RoleEnum.MEMBER)
                        .build()
        );

        Users sender = userRepository.getReferenceById(handleToGroupRequest.getSenderId());

        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(handleToGroupRequest.getSenderId())
                        .receiverId(handleToGroupRequest.getUserId())
                        .groupId(handleToGroupRequest.getGroupId())
                        .type("INVITE")
                        .title(sender.getFullName() + " mời bạn tham gia nhóm.")
                        .message("Bạn đã được " + sender.getFullName() + " mời vào nhóm " + groupMember.getGroup().getName())
                        .amount(null)
                        .build()
        );

        return groupMapper.toDto(groupMember.getGroup());
    }

    @Override
    public GroupDto leaveGroup(HandleToGroupRequest handleToGroupRequest) {
        if (!groupMemberRepository.existsById(new GroupMemberId(handleToGroupRequest.getGroupId(), handleToGroupRequest.getUserId()))) {
            throw new GroupException("Thành viên chưa có trong nhóm");
        }

        groupMemberRepository.delete(
                GroupMember.builder()
                        .id(new GroupMemberId(handleToGroupRequest.getGroupId(), handleToGroupRequest.getUserId()))
                        .group(groupRepository.getReferenceById(handleToGroupRequest.getGroupId()))
                        .user(userRepository.getReferenceById(handleToGroupRequest.getUserId()))
                        .role(RoleEnum.MEMBER)
                        .build()
        );

        return groupMapper.toDto(groupRepository.getReferenceById(handleToGroupRequest.getGroupId()));
    }

    @Override
    public List<UsersDto> viewMembersInGroup(UUID groupId) {
        List<GroupMember> members = groupMemberRepository.findAllById_GroupId(groupId);

        List<Users> result = new ArrayList<>();

        members.stream().map(GroupMember::getUser).forEach(result::add);

        return userMapper.toDtoList(result);
    }

    @Override
    public List<GroupDto> findUserByName(String groupName, UUID userId) {
        List<GroupMember> groupMember = groupMemberRepository.findAllById_UserId(userId);

        List<Group> groups = new ArrayList<>();
        for (GroupMember gm : groupMember) {
            if (gm.getGroup().getName().contains(groupName)) {
                groups.add(gm.getGroup());
            }
        }

        return groupMapper.toDtoList(groups);
    }

    @Override
    @Transactional
    public GroupDto updateInformation(UpdateGroupRequest request) {

        Group group = groupRepository.getReferenceById(request.getGroupId());
        group.setName(request.getGroupName());
        group.setDescription(request.getDescription());

        return groupMapper.toDto(groupRepository.save(group));
    }

    @Override
    @Transactional
    public GroupDto updateAvatar(UUID groupId, MultipartFile file) {
        String imageUrl = fileService.uploadFileUrl(file);

        Group group = groupRepository.getReferenceById(groupId);

        group.setAvatarUrl(imageUrl);

        return groupMapper.toDto(groupRepository.save(group));
    }

    @Override
    public void deleteGroupById(UUID groupId) {
        groupRepository.deleteById(groupId);
    }
}
