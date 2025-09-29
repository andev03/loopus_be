package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.GroupChatDto;
import com.loopus.loopus_be.dto.request.ChatTextRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IChatService {
    GroupChatDto sendMessage(ChatTextRequest request);

    List<GroupChatDto> getMessagesByGroupId(UUID groupId);

    List<GroupChatDto> findMessage(UUID groupId, String message);

    GroupChatDto sendImage(ChatTextRequest request, MultipartFile file);

    List<GroupChatDto> getAllImageType(UUID groupId);
}
