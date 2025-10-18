package com.loopus.loopus_be.controller.payous;

import com.loopus.loopus_be.dto.TransactionDto;
import com.loopus.loopus_be.enums.RoleEnum;
import com.loopus.loopus_be.enums.TransactionStatus;
import com.loopus.loopus_be.mapper.TransactionMapper;
import com.loopus.loopus_be.model.Transaction;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.TransactionRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IEmailService;
import com.loopus.loopus_be.service.OtpService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

@Controller
@RequiredArgsConstructor
public class CheckoutController {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final UserRepository userRepository;
    private final IEmailService emailService;
    private final OtpService otpService;

    private static final DecimalFormat AMOUNT_FORMATTER = new DecimalFormat("#,###");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    @RequestMapping("/success")
    public String handleSuccess(
            @RequestParam String status,
            @RequestParam("orderCode") String orderCode,
            Model model) {

        return handleTransaction(status, orderCode, model, true);
    }

    @RequestMapping("/cancel")
    public String handleCancel(
            @RequestParam String status,
            @RequestParam("orderCode") String orderCode, HttpServletRequest request,
            Model model) {
        System.out.println("Method: " + request.getMethod());
        System.out.println("Query: " + request.getQueryString());
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            System.out.println(name + ": " + request.getHeader(name));
        }
        return handleTransaction(status, orderCode, model, false);
    }

    private String handleTransaction(String status, String orderCode, Model model, boolean isSuccess) {
        Transaction transaction = transactionRepository.findByOrderCode(Long.parseLong(orderCode));

        if (transaction == null) {
            return "400BadRequest";
        }

        updateTransactionStatus(transaction, status);
        setTransactionModel(model, transaction);

        Users user = updateUserRoleIfMembershipSuccess(transaction);
        sendTransactionEmail(transaction, user, isSuccess);

        return isSuccess ? "success" : "cancel";
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

    private Users updateUserRoleIfMembershipSuccess(Transaction transaction) {
        Users user = userRepository.getReferenceById(transaction.getUser().getUserId());
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
