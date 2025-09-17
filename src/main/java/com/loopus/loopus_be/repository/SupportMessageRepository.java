package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.SupportMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupportMessageRepository extends JpaRepository<SupportMessage, UUID> {
}
