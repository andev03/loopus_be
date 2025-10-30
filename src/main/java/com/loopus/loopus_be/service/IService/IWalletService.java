package com.loopus.loopus_be.service.IService;

import com.loopus.loopus_be.dto.WalletDto;
import com.loopus.loopus_be.dto.WalletTransactionDto;
import com.loopus.loopus_be.dto.request.TransferRequest;
import com.loopus.loopus_be.model.Users;

import java.util.List;
import java.util.UUID;

public interface IWalletService {
    WalletDto getWalletByUserId(UUID userId);

    void transfer(TransferRequest request);

    List<WalletTransactionDto> getTransactions(UUID walletId);

    WalletTransactionDto getTransactionDetail(UUID walletTransactionId);

    void createWallet(Users user);

    WalletDto deposit(UUID userId, Double amount);

    void payout(UUID userId, Double amount);
}
