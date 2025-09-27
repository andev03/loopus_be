package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.ExpenseParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseParticipantRepository extends JpaRepository<ExpenseParticipant, UUID> {
}
