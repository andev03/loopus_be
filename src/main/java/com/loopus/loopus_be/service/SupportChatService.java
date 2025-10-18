package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.SupportChatDto;
import com.loopus.loopus_be.mapper.SupportChatMapper;
import com.loopus.loopus_be.mapper.SupportMessageMapper;
import com.loopus.loopus_be.model.SupportChat;
import com.loopus.loopus_be.repository.SupportChatRepository;
import com.loopus.loopus_be.repository.SupportMessageRepository;
import com.loopus.loopus_be.service.IService.ISupportChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportChatService implements ISupportChatService {

    private final SupportChatRepository supportChatRepository;
    private final SupportMessageRepository supportMessageRepository;
    private final SupportChatMapper supportChatMapper;
    private final SupportMessageMapper supportMessageMapper;

    @Override
    public List<SupportChatDto> getNotYetSupportChat(String status) {
        List<SupportChat> supportChats = supportChatRepository.findAllByStatus(status);

        return supportChatMapper.toDtoList(supportChats);
    }
}
