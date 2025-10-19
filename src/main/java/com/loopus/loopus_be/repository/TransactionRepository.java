package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.enums.TransactionType;
import com.loopus.loopus_be.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    List<Transaction> findAllByTransactionType(TransactionType transactionType);

    List<Transaction> findAllByUser_UserIdAndTransactionType(UUID userId, TransactionType transactionType);

    List<Transaction> findAllByUser_UserId(UUID userId);

    Transaction findByOrderCode(long l);
}
