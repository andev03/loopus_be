package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.SupportChatDto;
import com.loopus.loopus_be.dto.SupportMessageDto;

import java.util.List;
import java.util.UUID;

public interface ISupportChatService {

    List<SupportChatDto> getNotYetSupportChat(String status);

    SupportChatDto reception(UUID chatId, UUID adminId);

    List<SupportMessageDto> getAllMessageByChatId(UUID chatId);

    SupportMessageDto userChat(UUID userId, String message);

    SupportMessageDto adminChat(UUID adminId, UUID chatId, String message);
}
