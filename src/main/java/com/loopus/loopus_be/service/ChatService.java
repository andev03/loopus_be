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
import com.loopus.loopus_be.service.IService.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService implements IChatService {

    private final GroupChatRepository groupChatRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupChatMapper groupChatMapper;
    private final IFileService iFileService;

    @Override
    public GroupChatDto sendMessage(ChatTextRequest request) {
        if (request.getMessage() == null || request.getMessage().isEmpty()) {
            throw new IllegalArgumentException("Message không thể null hoặc rỗng");
        }
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

    @Override
    public List<GroupChatDto> findMessage(UUID groupId, String message) {

        List<GroupChat> groupChats = groupChatRepository.findByGroup_GroupIdOrderByCreatedAtAsc(groupId);

        List<GroupChat> result = new ArrayList<>();

        for (GroupChat groupChat : groupChats) {
            if (groupChat.getMessage().contains(message)) {
                result.add(groupChat);
            }
        }

        return groupChatMapper.toDtoList(result);
    }

    @Override
    public GroupChatDto sendImage(ChatTextRequest request, MultipartFile file) {
        String imageUrl = iFileService.uploadFileUrl(file);
        GroupChat groupChat = groupChatRepository.save(
                GroupChat.builder()
                        .group(groupRepository.getReferenceById(request.getGroupId()))
                        .sender(userRepository.getReferenceById(request.getUserId()))
                        .message(request.getMessage())
                        .type(MessageTypeEnum.IMAGE)
                        .imageUrl(imageUrl)
                        .build()
        );
        return groupChatMapper.toDto(groupChat);
    }

    @Override
    public List<GroupChatDto> getAllImageType(UUID groupId) {
        List<GroupChat> groupChats = groupChatRepository.findByGroup_GroupIdOrderByCreatedAtAsc(groupId);

        List<GroupChat> result = new ArrayList<>();

        for (GroupChat groupChat : groupChats) {
            if (groupChat.getType() == MessageTypeEnum.IMAGE) {
                result.add(groupChat);
            }
        }

        return groupChatMapper.toDtoList(result);
    }
}
