package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.WalletDto;
import com.loopus.loopus_be.dto.WalletTransactionDto;
import com.loopus.loopus_be.dto.request.CreateNotificationRequest;
import com.loopus.loopus_be.enums.TransactionType;
import com.loopus.loopus_be.exception.WalletException;
import com.loopus.loopus_be.mapper.WalletMapper;
import com.loopus.loopus_be.mapper.WalletTransactionMapper;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.model.Wallet;
import com.loopus.loopus_be.model.WalletTransaction;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.repository.WalletRepository;
import com.loopus.loopus_be.repository.WalletTransactionRepository;
import com.loopus.loopus_be.service.IService.INotificationService;
import com.loopus.loopus_be.service.IService.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService implements IWalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final UserRepository userRepository;
    private final INotificationService iNotificationService;
    private final WalletMapper walletMapper;
    private final WalletTransactionMapper walletTransactionMapper;

    @Override
    public WalletDto getWalletByUserId(UUID userId) {
        return walletMapper.toDto(
                walletRepository.findByUserUserId(userId)
                        .orElseThrow(() -> new WalletException("Wallet not found for user " + userId))
        );
    }

    @Override
    @Transactional
    public void transfer(UUID senderId, UUID receiverId, Double amount, UUID groupId) {
        Wallet senderWallet = walletRepository.findByUserUserId(senderId)
                .orElseThrow(() -> new WalletException("Wallet not found for user " + senderId));
        Wallet receiverWallet = walletRepository.findByUserUserId(receiverId)
                .orElseThrow(() -> new WalletException("Không tìm thấy ví user " + receiverId));

        if (senderWallet.getBalance() < amount) {
            throw new WalletException("Không đủ tiền để thanh toán. Vui lòng nạp thêm tiền vào ví!");
        }

        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

        WalletTransaction txOut = WalletTransaction.builder()
                .wallet(senderWallet)
                .amount(amount)
                .type(TransactionType.TRANSFER_OUT)
                .relatedUser(receiverWallet.getUser())
                .description("Chuyển tiền đến " + receiverWallet.getUser().getFullName())
                .build();

        // Ghi log giao dịch cho người nhận
        WalletTransaction txIn = WalletTransaction.builder()
                .wallet(receiverWallet)
                .amount(amount)
                .type(TransactionType.TRANSFER_IN)
                .relatedUser(senderWallet.getUser())
                .description("Nhận tiền từ " + senderWallet.getUser().getFullName())
                .build();

        notificationForSender(senderId, receiverId, amount, groupId);
        notificationForReceive(senderId, receiverId, amount, groupId);

        walletTransactionRepository.saveAll(List.of(txOut, txIn));
        walletRepository.saveAll(List.of(senderWallet, receiverWallet));
    }

    @Override
    public List<WalletTransactionDto> getTransactions(UUID walletId) {
        return walletTransactionMapper.toDtoList(
                walletTransactionRepository.findByWalletWalletIdOrderByCreatedAtDesc(walletId)
        );
    }

    @Override
    public WalletTransactionDto getTransactionDetail(UUID walletTransactionId) {
        return walletTransactionMapper.toDto(
                walletTransactionRepository.findById(walletTransactionId)
                        .orElseThrow(() -> new RuntimeException("Transaction not found"))
        );
    }

    @Override
    @Transactional
    public void createWallet(Users user) {
        walletMapper.toDto(walletRepository.save(Wallet.builder().user(user).balance(500000.00).build()));
    }

    private void notificationForSender(UUID senderId, UUID receiverId, Double amount, UUID groupId) {

        Users receiver = userRepository.getReferenceById(receiverId);

        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(null)
                        .receiverId(senderId)
                        .groupId(groupId)
                        .type("TRANSFER")
                        .title("Bạn đã trả " + amount)
                        .message("Bạn đã chuyển " + amount + " cho " + receiver.getFullName())
                        .amount(BigDecimal.valueOf(amount))
                        .build()
        );
    }

    private void notificationForReceive(UUID senderId, UUID receiverId, Double amount, UUID groupId) {

        Users sender = userRepository.getReferenceById(senderId);

        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(null)
                        .receiverId(receiverId)
                        .groupId(groupId)
                        .type("PAYMENT_RECEIVED")
                        .title("Bạn đã nhận " + amount)
                        .message("Bạn nhận được " + amount + " từ " + sender.getFullName())
                        .amount(BigDecimal.valueOf(amount))
                        .build()
        );
    }
}
