package com.loopus.loopus_be.mapper;

import com.loopus.loopus_be.dto.NotificationDto;
import com.loopus.loopus_be.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {UserMapper.class, GroupMapper.class})
public interface NotificationMapper {
    List<NotificationDto> toDtoList(List<Notification> models);

    NotificationDto toDto(Notification model);
}
