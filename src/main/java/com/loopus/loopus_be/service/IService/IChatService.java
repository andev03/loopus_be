package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.GroupChatDto;
import com.loopus.loopus_be.dto.request.ChatTextRequest;

import java.util.List;
import java.util.UUID;

public interface IChatService {
    GroupChatDto sendMessage(ChatTextRequest request);
    List<GroupChatDto> getMessagesByGroupId(UUID groupId);
}
