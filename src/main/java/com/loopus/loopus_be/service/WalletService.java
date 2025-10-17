package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.WalletDto;
import com.loopus.loopus_be.dto.WalletTransactionDto;
import com.loopus.loopus_be.dto.request.CreateNotificationRequest;
import com.loopus.loopus_be.dto.request.TransferRequest;
import com.loopus.loopus_be.enums.TransactionStatus;
import com.loopus.loopus_be.enums.TransactionType;
import com.loopus.loopus_be.exception.ExpenseException;
import com.loopus.loopus_be.exception.WalletException;
import com.loopus.loopus_be.mapper.WalletMapper;
import com.loopus.loopus_be.mapper.WalletTransactionMapper;
import com.loopus.loopus_be.model.*;
import com.loopus.loopus_be.repository.*;
import com.loopus.loopus_be.service.IService.INotificationService;
import com.loopus.loopus_be.service.IService.IWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private final ExpenseRepository expenseRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public WalletDto getWalletByUserId(UUID userId) {
        return walletMapper.toDto(
                walletRepository.findByUserUserId(userId)
                        .orElseThrow(() -> new WalletException("Wallet not found for user " + userId))
        );
    }

    @Override
    @Transactional
    public void transfer(TransferRequest request) {
        Wallet senderWallet = walletRepository.findByUserUserId(request.getSenderId())
                .orElseThrow(() -> new WalletException("Wallet not found for user " + request.getSenderId()));
        Wallet receiverWallet = walletRepository.findByUserUserId(request.getReceiverId())
                .orElseThrow(() -> new WalletException("Không tìm thấy ví user " + request.getReceiverId()));

        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new WalletException("Không đủ tiền để thanh toán. Vui lòng nạp thêm tiền vào ví!");
        }

        WalletTransaction txOut = WalletTransaction.builder()
                .wallet(senderWallet)
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER_OUT)
                .relatedUser(receiverWallet.getUser())
                .description("Chuyển tiền đến " + receiverWallet.getUser().getFullName())
                .build();

        // Ghi log giao dịch cho người nhận
        WalletTransaction txIn = WalletTransaction.builder()
                .wallet(receiverWallet)
                .amount(request.getAmount())
                .type(TransactionType.TRANSFER_IN)
                .relatedUser(senderWallet.getUser())
                .description("Nhận tiền từ " + senderWallet.getUser().getFullName())
                .build();


        if (request.getTypeTransfer().equals("GROUP_EXPENSE")) {
            changeExpenseWhenTransferByGroupType(
                    request.getSenderId(), request.getExpenseId(), request, senderWallet, receiverWallet, txOut, txIn
            );
        } else {
            changeExpenseWhenTransferByIndividualType(
                    request.getSenderId(), request.getReceiverId(),
                    request, senderWallet, receiverWallet, txOut, txIn
            );
        }

    }

    private void changeExpenseWhenTransferByIndividualType(
            UUID senderId, UUID receiverId, TransferRequest request, Wallet senderWallet,
            Wallet receiverWallet, WalletTransaction txOut, WalletTransaction txIn
    ) {
        List<Expense> expenses = expenseRepository.findAllByPaidBy_UserId(receiverId);
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();
        for (Expense expense : expenses) {
            expenseParticipants.addAll(expense.getParticipants());
        }

        for (ExpenseParticipant expenseParticipant : expenseParticipants) {
            if (expenseParticipant.getUser().getUserId().equals(senderId) && !expenseParticipant.isPaid()) {
                expenseParticipant.setPaid(true);
                senderWallet.setBalance(senderWallet.getBalance().subtract(expenseParticipant.getShareAmount()));
                receiverWallet.setBalance(senderWallet.getBalance().add(expenseParticipant.getShareAmount()));
            }
        }

        notificationForSender(
                request.getSenderId(), request.getReceiverId(),
                request.getAmount(), request.getGroupId()
        );

        notificationForReceive(
                request.getSenderId(), request.getReceiverId(),
                request.getAmount(), request.getGroupId()
        );

        walletTransactionRepository.saveAll(List.of(txOut, txIn));
        walletRepository.saveAll(List.of(senderWallet, receiverWallet));
        expenseRepository.saveAll(expenses);
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
        walletMapper.toDto(walletRepository.save(Wallet.builder().user(user).balance(new BigDecimal("500000.00")).build()));
    }

    @Override
    public WalletDto deposit(UUID userId, Double amount) {

        Users user = userRepository.getReferenceById(userId);

        Wallet wallet = walletRepository.findByUserUserId(userId).orElseThrow(
                () -> new WalletException("Không tìm thấy ví!")
        );


        wallet.setBalance(user.getWallet().getBalance().add(BigDecimal.valueOf(amount)));

        walletRepository.save(wallet);
        walletTransactionRepository.save(
                WalletTransaction.builder()
                        .wallet(wallet)
                        .amount(BigDecimal.valueOf(amount))
                        .description("Bạn đã nạp " + amount + " vào ví.")
                        .type(TransactionType.DEPOSIT)
                        .relatedUser(null)
                        .build()
        );
        walletDepositNotification(userId, amount);
        transactionRepository.save(
                Transaction.builder()
                        .orderCode(System.currentTimeMillis() / 1000)
                        .user(user)
                        .amount(BigDecimal.valueOf(amount))
                        .transactionType(TransactionType.DEPOSIT)
                        .status(TransactionStatus.PAID)
                        .description(user.getFullName() + "đã nạp " + amount + " vào ví.")
                        .build()
        );
        return walletMapper.toDto(wallet);
    }

    private void walletDepositNotification(UUID receiveId, Double amount) {
        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(null)
                        .receiverId(receiveId)
                        .groupId(null)
                        .type("DEPOSIT")
                        .title("Nạp tiền thành công")
                        .message("Bạn đã nạp " + amount + " vào ví.")
                        .amount(BigDecimal.valueOf(amount))
                        .build()
        );
    }

    private void notificationForSender(UUID senderId, UUID receiverId, BigDecimal amount, UUID groupId) {

        Users receiver = userRepository.getReferenceById(receiverId);

        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(null)
                        .receiverId(senderId)
                        .groupId(groupId)
                        .type("TRANSFER")
                        .title("Bạn đã trả " + amount)
                        .message("Bạn đã chuyển " + amount + " cho " + receiver.getFullName())
                        .amount(amount)
                        .build()
        );
    }

    private void notificationForReceive(UUID senderId, UUID receiverId, BigDecimal amount, UUID groupId) {

        Users sender = userRepository.getReferenceById(senderId);

        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(null)
                        .receiverId(receiverId)
                        .groupId(groupId)
                        .type("PAYMENT_RECEIVED")
                        .title("Bạn đã nhận " + amount)
                        .message("Bạn nhận được " + amount + " từ " + sender.getFullName())
                        .amount(amount)
                        .build()
        );
    }

    private void changeExpenseWhenTransferByGroupType(
            UUID senderId, UUID expenseId, TransferRequest request, Wallet senderWallet, Wallet receiverWallet,
            WalletTransaction txOut, WalletTransaction txIn
    ) {
        Expense expense = expenseRepository.findById(expenseId).orElseThrow(
                () -> new ExpenseException("Không tìm thấy chi tiêu!")
        );

        for (ExpenseParticipant participant : expense.getParticipants()) {
            if (participant.getUser().getUserId().equals(senderId)) {
                participant.setPaid(true);
            }
        }
        notificationForSender(
                request.getSenderId(), request.getReceiverId(),
                request.getAmount(), request.getGroupId()
        );

        notificationForReceive(
                request.getSenderId(), request.getReceiverId(),
                request.getAmount(), request.getGroupId()
        );

        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));
        receiverWallet.setBalance(senderWallet.getBalance().add(request.getAmount()));

        walletTransactionRepository.saveAll(List.of(txOut, txIn));
        walletRepository.saveAll(List.of(senderWallet, receiverWallet));
        expenseRepository.save(expense);
    }
}
