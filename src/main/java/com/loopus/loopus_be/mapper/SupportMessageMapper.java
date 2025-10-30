package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.SupportMessageDto;
import com.loopus.loopus_be.model.SupportMessage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, SupportMessageMapper.class})
public interface SupportMessageMapper {

    List<SupportMessageDto> toDtoList(List<SupportMessage> models);

    @Mapping(target = "chatId", source = "chat.chatId")
    SupportMessageDto toDto(SupportMessage model);
}
