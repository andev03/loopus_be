package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
}
