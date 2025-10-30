package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BankRepository extends JpaRepository<Bank, UUID> {
}
