package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.SupportChatDto;
import com.loopus.loopus_be.model.SupportChat;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class})
public interface SupportChatMapper {
    List<SupportChatDto> toDtoList(List<SupportChat> models);

    SupportChatDto toDto(SupportChat model);
}
