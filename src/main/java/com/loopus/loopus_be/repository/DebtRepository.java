package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DebtRepository extends JpaRepository<Debt, UUID> {
}
