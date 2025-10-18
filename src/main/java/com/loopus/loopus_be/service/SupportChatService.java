package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.SupportChatDto;
import com.loopus.loopus_be.dto.SupportMessageDto;
import com.loopus.loopus_be.mapper.SupportChatMapper;
import com.loopus.loopus_be.mapper.SupportMessageMapper;
import com.loopus.loopus_be.model.SupportChat;
import com.loopus.loopus_be.model.SupportMessage;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.SupportChatRepository;
import com.loopus.loopus_be.repository.SupportMessageRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.ISupportChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.tags.form.AbstractDataBoundFormElementTag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SupportChatService implements ISupportChatService {

    private final SupportChatRepository supportChatRepository;
    private final SupportMessageRepository supportMessageRepository;
    private final SupportChatMapper supportChatMapper;
    private final SupportMessageMapper supportMessageMapper;
    private final UserRepository userRepository;

    @Override
    public List<SupportChatDto> getNotYetSupportChat(String status) {
        List<SupportChat> supportChats = supportChatRepository.findAllByStatus(status);

        return supportChatMapper.toDtoList(supportChats);
    }

    @Override
    @Transactional
    public SupportChatDto reception(UUID chatId, UUID adminId) {
        SupportChat supportChat = supportChatRepository.getReferenceById(chatId);

        Users users = userRepository.getReferenceById(adminId);

        supportChat.setAdmin(users);
        supportChat.setStatus("RECEPTION");

        supportChatRepository.save(supportChat);

        SupportChatDto supportChatDto = supportChatMapper.toDto(supportChat);

        SupportMessageDto supportMessageDto = supportMessageMapper.toDto(
                supportMessageRepository.save(
                        SupportMessage.builder()
                                .chat(supportChat)
                                .sender(supportChat.getUser())
                                .message("Chào bạn, chúng tôi có thể giúp gì cho bạn!")
                                .build()
                )
        );

        List<SupportMessageDto> supportMessageDtos = new ArrayList<>();

        supportMessageDtos.add(supportMessageDto);

        supportChatDto.setSupportMessageDtos(supportMessageDtos);

        return supportChatDto;
    }

    @Override
    public List<SupportMessageDto> getAllMessageByChatId(UUID chatId) {
        List<SupportMessage> supportMessages = supportMessageRepository.findAllByChat_ChatId(chatId);

        return supportMessageMapper.toDtoList(supportMessages);
    }

    @Override
    public SupportMessageDto userChat(UUID userId, String message) {

        Users users = userRepository.getReferenceById(userId);

        SupportChat supportChat = supportChatRepository.findByUser(users);

        if (supportChat == null) {
            supportChat = supportChatRepository.save(
                    SupportChat.builder()
                            .user(users)
                            .admin(null)
                            .build()
            );
        }

        SupportMessage supportMessage = supportMessageRepository.save(
                SupportMessage.builder()
                        .chat(supportChat)
                        .sender(supportChat.getUser())
                        .message(message)
                        .build()
        );

        return supportMessageMapper.toDto(supportMessage);
    }

    @Override
    public SupportMessageDto adminChat(UUID adminId, UUID chatId, String message) {

        SupportChat supportChat = supportChatRepository.getReferenceById(chatId);

        SupportMessage supportMessage = supportMessageRepository.save(
                SupportMessage.builder()
                        .sender(userRepository.getReferenceById(adminId))
                        .chat(supportChat)
                        .message(message)
                        .build()
        );

        return supportMessageMapper.toDto(supportMessage);
    }
}
