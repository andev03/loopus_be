package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.NotificationDto;
import com.loopus.loopus_be.dto.request.CreateNotificationRequest;

import java.util.List;
import java.util.UUID;

public interface INotificationService {
    void createNotification(CreateNotificationRequest request);

    List<NotificationDto> getNotifications(UUID userId);

    void markAsRead(UUID notificationId);

    void markAllAsRead(UUID userId);
}
