package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.SupportChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupportChatRepository extends JpaRepository<SupportChat, UUID> {
}
