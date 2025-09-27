package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.GroupChatDto;
import com.loopus.loopus_be.model.GroupChat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupChatMapper {
    List<GroupChatDto> toDtoList(List<GroupChat> models);

    @Mapping(target = "senderId", source = "sender.userId")
    @Mapping(target = "avatarUrl", source = "sender.avatarUrl")
    @Mapping(target = "senderName", source = "sender.fullName")
    GroupChatDto toDto(GroupChat model);
}
