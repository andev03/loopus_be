package com.loopus.loopus_be.repository;

import com.loopus.loopus_be.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, UUID> {
    List<WalletTransaction> findAllByWallet_WalletIdOrderByCreatedAtDesc(UUID walletId);
}
