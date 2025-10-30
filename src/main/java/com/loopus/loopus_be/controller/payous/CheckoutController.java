package com.loopus.loopus_be.controller.payous;

import com.loopus.loopus_be.dto.TransactionDto;
import com.loopus.loopus_be.enums.RoleEnum;
import com.loopus.loopus_be.enums.TransactionStatus;
import com.loopus.loopus_be.mapper.TransactionMapper;
import com.loopus.loopus_be.model.Transaction;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.TransactionRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.repository.WalletRepository;
import com.loopus.loopus_be.service.IService.IEmailService;
import com.loopus.loopus_be.service.IService.IWalletService;
import com.loopus.loopus_be.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final IEmailService emailService;
    private final OtpService otpService;
    private final IWalletService iWalletService;

    private static final DecimalFormat AMOUNT_FORMATTER = new DecimalFormat("#,###");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @RequestMapping("/success")
    public String handleSuccess(
            @RequestParam String status,
            @RequestParam("orderCode") String orderCode,
            Model model) {

        return handleTransaction(status, orderCode, model, true);
    }

    @RequestMapping("/success_deposit")
    public String handleSuccessDeposit(
            @RequestParam String status,
            @RequestParam("orderCode") String orderCode,
            Model model) {

        Transaction transaction = transactionRepository.findByOrderCode(Long.parseLong(orderCode));

        iWalletService.deposit(
                transaction.getUser().getUserId(), Double.valueOf(transaction.getAmount().toString())
        );

        return handleTransactionForDeposit(status, orderCode, model, true);
    }


    @RequestMapping("/cancel")
    public String handleCancel(
            @RequestParam String status,
            @RequestParam("orderCode") String orderCode,
            Model model) {

        return handleTransaction(status, orderCode, model, false);
    }

    @RequestMapping("/cancel_deposit")
    public String handleCancelDeposit(
            @RequestParam String status,
            @RequestParam("orderCode") String orderCode,
            Model model) {

        return handleTransactionForDeposit(status, orderCode, model, false);
    }

    private String handleTransaction(String status, String orderCode, Model model, boolean isSuccess) {
        Transaction transaction = transactionRepository.findByOrderCode(Long.parseLong(orderCode));

        if (transaction == null) {
            return "400BadRequest";
        }

        updateTransactionStatus(transaction, status);
        setTransactionModel(model, transaction);

        Users user = updateUserRoleIfMembershipSuccess(transaction, TransactionStatus.valueOf(status));
        sendTransactionEmail(transaction, user, isSuccess);

        return isSuccess ? "success" : "cancel";
    }

    private String handleTransactionForDeposit(String status, String orderCode, Model model, boolean isSuccess) {
        Transaction transaction = transactionRepository.findByOrderCode(Long.parseLong(orderCode));

        if (transaction == null) {
            return "400BadRequest";
        }

        updateTransactionStatus(transaction, status);

        setTransactionModel(model, transaction);

        Users user = transaction.getUser();

        sendTransactionEmail(transaction, user, isSuccess);

        return isSuccess ? "success_deposit" : "cancel_deposit";
    }

    private void updateTransactionStatus(Transaction transaction, String status) {
        if (transaction.getStatus() == TransactionStatus.PENDING) {
            transaction.setStatus(TransactionStatus.valueOf(status));
            transactionRepository.save(transaction);
        }
    }

    private void setTransactionModel(Model model, Transaction transaction) {
        TransactionDto dto = transactionMapper.toDto(transaction);

        model.addAttribute("orderCode", dto.getOrderCode());
        model.addAttribute("amount", AMOUNT_FORMATTER.format(dto.getAmount()));
        model.addAttribute("time", dto.getCreatedAt().format(DATE_FORMATTER));
        model.addAttribute("transactionId", dto.getTransactionId());
        model.addAttribute("paymentType", dto.getTransactionType());
        model.addAttribute("description", dto.getDescription());
        model.addAttribute("status", dto.getStatus());
    }

    private Users updateUserRoleIfMembershipSuccess(Transaction transaction, TransactionStatus status) {
        Users user = userRepository.getReferenceById(transaction.getUser().getUserId());

        if (status.equals(TransactionStatus.CANCELLED)) {
            return user;
        }

        user.setRole(RoleEnum.MEMBER);
        return userRepository.save(user);
    }

    private void sendTransactionEmail(Transaction transaction, Users user, boolean isSuccess) {
        String formattedAmount = AMOUNT_FORMATTER.format(transaction.getAmount());
        String formattedDate = transaction.getCreatedAt().format(DATE_FORMATTER);
        String otp = otpService.generateOtp(user.getUsername());

        if (isSuccess) {
            emailService.sendOtpEmailForSuccess(
                    user.getUsername(),
                    otp,
                    String.valueOf(transaction.getTransactionId()),
                    formattedAmount,
                    formattedDate,
                    String.valueOf(transaction.getOrderCode()),
                    String.valueOf(transaction.getTransactionType()),
                    transaction.getDescription(),
                    String.valueOf(transaction.getStatus())
            );
        } else {
            emailService.sendOtpEmailForFailed(
                    user.getUsername(),
                    otp,
                    String.valueOf(transaction.getTransactionId()),
                    formattedAmount,
                    formattedDate,
                    String.valueOf(transaction.getOrderCode()),
                    String.valueOf(transaction.getTransactionType()),
                    transaction.getDescription(),
                    String.valueOf(transaction.getStatus())
            );
        }
    }
}
