package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.NotificationDto;
import com.loopus.loopus_be.dto.request.CreateNotificationRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface INotificationService {
    NotificationDto createNotification(CreateNotificationRequest request);

    List<NotificationDto> getNotifications(UUID userId);

    void markAsRead(UUID notificationId);

    void markAllAsRead(UUID userId);
}
