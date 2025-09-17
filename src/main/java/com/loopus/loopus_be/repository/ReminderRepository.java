package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Reminder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReminderRepository extends JpaRepository<Reminder, UUID> {
}
