package com.loopus.loopus_be.service;

import com.loopus.loopus_be.dto.ExpenseDto;
import com.loopus.loopus_be.dto.ExpenseParticipantDto;
import com.loopus.loopus_be.dto.request.*;
import com.loopus.loopus_be.dto.response.ExpenseParticipantAllDto;
import com.loopus.loopus_be.mapper.ExpenseMapper;
import com.loopus.loopus_be.mapper.ExpenseParticipantMapper;
import com.loopus.loopus_be.model.Expense;
import com.loopus.loopus_be.model.ExpenseParticipant;
import com.loopus.loopus_be.model.Group;
import com.loopus.loopus_be.model.Users;
import com.loopus.loopus_be.repository.ExpenseParticipantRepository;
import com.loopus.loopus_be.repository.ExpenseRepository;
import com.loopus.loopus_be.repository.GroupRepository;
import com.loopus.loopus_be.repository.UserRepository;
import com.loopus.loopus_be.service.IService.IExpenseService;
import com.loopus.loopus_be.service.IService.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpenseService implements IExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final ExpenseParticipantRepository expenseParticipantRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final ExpenseParticipantMapper expenseParticipantMapper;
    private final INotificationService iNotificationService;

    @Override
    public List<ExpenseDto> getAllByGroupId(UUID groupId) {
        return expenseMapper.toDtoList(expenseRepository.findAllByGroup_GroupId(groupId));
    }

    @Override
    @Transactional
    public ExpenseDto createExpense(CreateExpenseRequest request) {

        Expense expense = null;
        if (request.getType().equalsIgnoreCase("equal")) {

            expense = expenseRepository.save(
                    Expense.builder()
                            .description(request.getDescription())
                            .amount(request.getAmount())
                            .group(groupRepository.getReferenceById(request.getGroupId()))
                            .paidBy(userRepository.getReferenceById(request.getPaidById()))
                            .build()
            );

            expense.setParticipants(saveExpenseParticipantsForEquals(request, expense, request.getUserId()));

        } else {

            BigDecimal otherShareAmount = calculateTotalShareAmount(request.getExpenseParticipant());

            expense = expenseRepository.save(
                    Expense.builder()
                            .description(request.getDescription())
                            .amount(otherShareAmount)
                            .group(groupRepository.getReferenceById(request.getGroupId()))
                            .paidBy(userRepository.getReferenceById(request.getPaidById()))
                            .build()
            );

            expense.setParticipants(saveExpenseParticipantsForOther(request, expense, request.getUserId()));
        }


        return expenseMapper.toDto(expense);
    }

    @Override
    @Transactional
    public ExpenseDto updateExpense(UpdateExpenseRequest request) {
        Expense expense = null;

        if (request.getType().equalsIgnoreCase("equal")) {
            expense = expenseRepository.getReferenceById(request.getExpenseId());

            expense.setPaidBy(userRepository.getReferenceById(request.getPaidById()));
            expense.setDescription(request.getDescription());
            expense.setAmount(request.getAmount());

            expense = expenseRepository.save(expense);

            expense.setParticipants(updateExpenseParticipantsForEquals(request));
        } else {

            BigDecimal otherShareAmount = calculateTotalShareAmountUpdate(request.getExpenseParticipant());

            expense = expenseRepository.getReferenceById(request.getExpenseId());

            expense.setPaidBy(userRepository.getReferenceById(request.getPaidById()));
            expense.setDescription(request.getDescription());
            expense.setAmount(otherShareAmount);

            expense = expenseRepository.save(expense);

            expense.setParticipants(updateExpenseParticipantsForOther(request));
        }

        return expenseMapper.toDto(expense);
    }

    @Override
    public void deleteExpense(UUID expenseId) {
        Expense expense = expenseRepository.getReferenceById(expenseId);

        expenseRepository.delete(expense);
    }

    @Override
    public List<ExpenseParticipantDto> getExpenseToDebtReminder(UUID expenseId) {

        Expense expense = expenseRepository.getReferenceById(expenseId);

        return expenseParticipantMapper.toDtoList(expense.getParticipants());
    }

    @Override
    public List<ExpenseParticipantDto> getExpenseToDebtReminderIndividual(UUID userId, UUID payerId) {
        List<Expense> expenses = expenseRepository.findAllByPaidBy_UserId(userId);

        List<ExpenseParticipant> expenseParticipants = expenseParticipantRepository.findAllByExpenseInAndUser_UserId(expenses, payerId);

        List<ExpenseParticipantDto> result = new ArrayList<>();

        for (Expense expense : expenses) {
            for (ExpenseParticipant expenseParticipant : expenseParticipants) {
                if (expenseParticipant.getExpense().getExpenseId().equals(expense.getExpenseId())) {
                    expense.setParticipants(null);

                    ExpenseParticipantDto dto = expenseParticipantMapper.toDto(expenseParticipant);

                    dto.setExpenseDto(expenseMapper.toDto(expense));

                    result.add(dto);
                }
            }
        }

        return result;
    }

    @Override
    public List<ExpenseParticipantDto> debtReminderGroup(UUID expenseId, UUID userId) {

        Expense expense = expenseRepository.getReferenceById(expenseId);

        for (ExpenseParticipant participant : expense.getParticipants()) {
            if (!participant.getUser().getUserId().equals(userId)) {
                notificationDebtReminder(
                        userId, participant.getUser().getUserId(),
                        expense.getGroup().getGroupId(), participant.getShareAmount()
                );
            }
        }

        return expenseParticipantMapper.toDtoList(expense.getParticipants());
    }

    @Override
    public List<ExpenseParticipantDto> debtReminderIndividual(UUID userId, UUID payerId) {

        List<Expense> expense = expenseRepository.findAllByPaidBy_UserId(userId);

        List<ExpenseParticipant> expenseParticipants = expenseParticipantRepository.findAllByExpenseInAndUser_UserId(expense, payerId);

        BigDecimal amount = BigDecimal.ZERO;
        for (ExpenseParticipant expenseParticipant : expenseParticipants) {
            amount = amount.add(expenseParticipant.getShareAmount());
        }

        notificationDebtReminderIndividual(userId, payerId, amount);

        return expenseParticipantMapper.toDtoList(expenseParticipants);
    }

    @Override
    public ExpenseDto getExpenseByExpenseId(UUID getExpenseByExpenseId) {
        return expenseMapper.toDto(expenseRepository.getReferenceById(getExpenseByExpenseId));
    }

    @Override
    @Transactional
    public List<ExpenseParticipantAllDto> debtReminderAll(UUID userId) {
        List<Expense> expenses = expenseRepository.findAllByPaidBy_UserId(userId);

        List<ExpenseParticipantAllDto> expenseParticipantAllDtos = expenseParticipantAllDto(expenses, userId);

        for (ExpenseParticipantAllDto dto : expenseParticipantAllDtos) {
            notificationDebtReminderIndividual(
                    userId, dto.getDebtorId(), dto.getTotalOwedAmount()
            );
        }

        return expenseParticipantAllDtos;
    }

    @Override
    public List<ExpenseParticipantAllDto> getExpenseToDebtReminderAll(UUID userId) {
        List<Expense> expenses = expenseRepository.findAllByPaidBy_UserId(userId);

        return expenseParticipantAllDto(expenses, userId);
    }

    private List<ExpenseParticipantAllDto> expenseParticipantAllDto(
            List<Expense> expenses, UUID userId

    ) {
        Map<UUID, BigDecimal> debtMap = new HashMap<>();
        Map<UUID, String> nameMap = new HashMap<>();

        for (Expense expense : expenses) {
            for (ExpenseParticipant participant : expense.getParticipants()) {
                if (participant.getUser().getUserId().equals(userId)) continue;

                UUID debtorId = participant.getUser().getUserId();
                BigDecimal owed = participant.getShareAmount();

                debtMap.put(
                        debtorId,
                        debtMap.getOrDefault(debtorId, BigDecimal.ZERO).add(owed)
                );

                nameMap.put(debtorId, participant.getUser().getFullName());
            }
        }

        List<ExpenseParticipantAllDto> result = new ArrayList<>();
        for (UUID debtorId : debtMap.keySet()) {
            ExpenseParticipantAllDto dto = new ExpenseParticipantAllDto();
            dto.setDebtorId(debtorId);
            dto.setDebtorName(nameMap.get(debtorId));
            dto.setPaidToUserId(userId);
            dto.setTotalOwedAmount(debtMap.get(debtorId));

            result.add(dto);
        }

        return result;
    }

    @Transactional
    private List<ExpenseParticipant> updateExpenseParticipantsForEquals(
            UpdateExpenseRequest request
    ) {
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();

        BigDecimal equalShareAmount = calculateTotalShareAmountForEqualUpdate(request);

        for (UpdateExpenseParticipantRequest updateExpenseParticipantRequest : request.getExpenseParticipant()) {
            ExpenseParticipant expenseParticipant =
                    expenseParticipantRepository.getReferenceById(updateExpenseParticipantRequest.getExpenseParticipantId());

            expenseParticipant.setShareAmount(equalShareAmount);

            expenseParticipants.add(expenseParticipantRepository.save(expenseParticipant));
        }

        return expenseParticipants;
    }


    @Transactional
    private List<ExpenseParticipant> updateExpenseParticipantsForOther(
            UpdateExpenseRequest request
    ) {
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();

        for (UpdateExpenseParticipantRequest updateExpenseParticipantRequest : request.getExpenseParticipant()) {
            ExpenseParticipant expenseParticipant =
                    expenseParticipantRepository.getReferenceById(updateExpenseParticipantRequest.getExpenseParticipantId());
            expenseParticipant.setShareAmount(updateExpenseParticipantRequest.getShareAmount());

            expenseParticipants.add(expenseParticipantRepository.save(expenseParticipant));
        }

        return expenseParticipants;
    }

    @Transactional
    private List<ExpenseParticipant> saveExpenseParticipantsForEquals(
            CreateExpenseRequest request, Expense expense, UUID userId
    ) {
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();

        BigDecimal equalShareAmount = calculateTotalShareAmountForEqual(request);

        boolean isPaid = false;
        for (CreateExpenseParticipantRequest createExpenseParticipantRequest : request.getExpenseParticipant()) {
            if (createExpenseParticipantRequest.getUserId().equals(userId)) {
                isPaid = true;
            }
            expenseParticipants.add(
                    expenseParticipantRepository.save(
                            ExpenseParticipant.builder()
                                    .expense(expense)
                                    .user(userRepository.getReferenceById(createExpenseParticipantRequest.getUserId()))
                                    .shareAmount(equalShareAmount)
                                    .isPaid(isPaid)
                                    .build()
                    )
            );
            isPaid = false;
        }

        return expenseParticipants;
    }

    @Transactional
    private List<ExpenseParticipant> saveExpenseParticipantsForOther(
            CreateExpenseRequest request, Expense expense, UUID userId
    ) {
        List<ExpenseParticipant> expenseParticipants = new ArrayList<>();

        boolean isPaid = false;

        for (CreateExpenseParticipantRequest createExpenseParticipantRequest : request.getExpenseParticipant()) {
            if (createExpenseParticipantRequest.getUserId().equals(userId)) {
                isPaid = true;
            }
            expenseParticipants.add(
                    expenseParticipantRepository.save(
                            ExpenseParticipant.builder()
                                    .expense(expense)
                                    .user(userRepository.getReferenceById(createExpenseParticipantRequest.getUserId()))
                                    .shareAmount(createExpenseParticipantRequest.getShareAmount())
                                    .isPaid(isPaid)
                                    .build()
                    )
            );

            isPaid = false;
        }

        return expenseParticipants;
    }

    private BigDecimal calculateTotalShareAmount(List<CreateExpenseParticipantRequest> request) {

        BigDecimal totalShareAmount = BigDecimal.ZERO;

        for (CreateExpenseParticipantRequest expenseParticipantRequest : request) {
            totalShareAmount = totalShareAmount.add(expenseParticipantRequest.getShareAmount());
        }

        return totalShareAmount;
    }

    private BigDecimal calculateTotalShareAmountForEqual(CreateExpenseRequest request) {

        return request.getAmount().divide(BigDecimal.valueOf(request.getExpenseParticipant().size()), 2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTotalShareAmountUpdate(List<UpdateExpenseParticipantRequest> request) {

        BigDecimal totalShareAmount = BigDecimal.ZERO;

        for (UpdateExpenseParticipantRequest expenseParticipantRequest : request) {
            totalShareAmount = totalShareAmount.add(expenseParticipantRequest.getShareAmount());
        }

        return totalShareAmount;
    }

    private BigDecimal calculateTotalShareAmountForEqualUpdate(UpdateExpenseRequest request) {

        return request.getAmount().divide(BigDecimal.valueOf(request.getExpenseParticipant().size()), 2, RoundingMode.HALF_UP);
    }

    private void notificationDebtReminder(UUID senderId, UUID receiveId, UUID groupId, BigDecimal amount) {

        Users sender = userRepository.getReferenceById(senderId);
        Group group = groupRepository.getReferenceById(groupId);

        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(senderId)
                        .receiverId(receiveId)
                        .groupId(groupId)
                        .type("PAYMENT_REMINDER")
                        .title(sender.getFullName() + " đã nhắc bạn trả tiền nhóm!")
                        .message(sender.getFullName() + " đã nhắc bạn trả " + amount + " trong nhóm  " + group.getName())
                        .amount(amount)
                        .build()
        );
    }

    private void notificationDebtReminderIndividual(UUID senderId, UUID receiveId, BigDecimal amount) {

        Users sender = userRepository.getReferenceById(senderId);

        iNotificationService.createNotification(
                CreateNotificationRequest.builder()
                        .senderId(senderId)
                        .receiverId(receiveId)
                        .groupId(null)
                        .type("PAYMENT_REMINDER")
                        .title(sender.getFullName() + " đã nhắc bạn trả tiền cá nhân!")
                        .message(sender.getFullName() + " đã nhắc bạn trả tiền tổng là " + amount)
                        .amount(amount)
                        .build()
        );
    }
}
