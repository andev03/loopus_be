package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.GroupChatDto;
import com.loopus.loopus_be.dto.request.ChatTextRequest;
import com.loopus.loopus_be.enums.MessageTypeEnum;
import com.loopus.loopus_be.mapper.GroupChatMapper;
import com.loopus.loopus_be.model.GroupChat;
import com.loopus.loopus_be.repository.GroupChatRepository;
import com.loopus.loopus_be.repository.GroupRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {

    private final GroupChatRepository groupChatRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupChatMapper groupChatMapper;

    @Override
    public GroupChatDto sendMessage(ChatTextRequest request) {
        GroupChat groupChat = groupChatRepository.save(
                GroupChat.builder()
                        .group(groupRepository.getReferenceById(request.getGroupId()))
                        .sender(userRepository.getReferenceById(request.getUserId()))
                        .message(request.getMessage())
                        .type(MessageTypeEnum.TEXT)
                        .build()
        );
        return groupChatMapper.toDto(groupChat);
    }

    @Override
    public List<GroupChatDto> getMessagesByGroupId(UUID groupId) {

        List<GroupChat> groupChats = groupChatRepository.findByGroup_GroupIdOrderByCreatedAtAsc(groupId);

        return groupChatMapper.toDtoList(groupChats);
    }
}
