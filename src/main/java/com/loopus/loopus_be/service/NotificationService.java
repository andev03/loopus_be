package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.NotificationDto;
import com.loopus.loopus_be.dto.request.CreateNotificationRequest;
import com.loopus.loopus_be.mapper.NotificationMapper;
import com.loopus.loopus_be.model.Group;
import com.loopus.loopus_be.model.Notification;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.GroupRepository;
import com.loopus.loopus_be.repository.NotificationRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.INotificationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService implements INotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    @Override
    @Transactional
    public void createNotification(CreateNotificationRequest request) {
        Users receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new EntityNotFoundException("Receiver not found"));
        Users sender = request.getSenderId() != null ? userRepository.findById(request.getSenderId()).orElse(null) : null;
        Group group = request.getGroupId() != null ? groupRepository.findById(request.getGroupId()).orElse(null) : null;

        Notification notification = Notification.builder()
                .user(receiver)
                .sender(sender)
                .group(group)
                .type(request.getType())
                .title(request.getTitle())
                .message(request.getMessage())
                .amount(request.getAmount())
                .isRead(false)
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        notificationMapper.toDto(notificationRepository.save(notification));
    }

    @Override
    public List<NotificationDto> getNotifications(UUID userId) {
        return notificationMapper.toDtoList(notificationRepository.findByUser_UserIdOrderByCreatedAtDesc(userId));
    }

    @Override
    @Transactional
    public void markAsRead(UUID notificationId) {
        Notification n = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        n.setIsRead(true);
        n.setUpdatedAt(Instant.now());
        notificationRepository.save(n);
    }

    @Override
    @Transactional
    public void markAllAsRead(UUID userId) {
        List<Notification> unread = notificationRepository.findUnreadByUserId(userId);
        unread.forEach(n -> {
            n.setIsRead(true);
            n.setUpdatedAt(Instant.now());
        });
        notificationRepository.saveAll(unread);
    }
}
