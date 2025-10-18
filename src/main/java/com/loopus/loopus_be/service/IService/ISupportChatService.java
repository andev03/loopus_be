package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.SupportChatDto;

import java.util.List;

public interface ISupportChatService {

    List<SupportChatDto> getNotYetSupportChat(String status);
}
